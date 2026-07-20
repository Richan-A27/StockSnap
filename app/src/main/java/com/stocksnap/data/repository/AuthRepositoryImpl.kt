package com.stocksnap.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.stocksnap.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        firebaseAuth.addAuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                scope.launch {
                    try {
                        val doc = firestore.collection("users").document(firebaseUser.uid).get().await()
                        val u = doc.toObject(User::class.java)
                        if (u != null) {
                            _currentUser.value = u
                        } else {
                            // First-time user — check if users collection is empty
                            val usersSnapshot = firestore.collection("users").limit(1).get().await()
                            val isFirstUser = usersSnapshot.isEmpty

                            val newUser = User(
                                uid = firebaseUser.uid,
                                name = firebaseUser.displayName ?: "",
                                email = firebaseUser.email ?: "",
                                photoUrl = firebaseUser.photoUrl?.toString(),
                                role = if (isFirstUser) "ADMIN" else "EMPLOYEE",
                                active = isFirstUser,
                                approvalStatus = if (isFirstUser) "APPROVED" else "PENDING",
                                lastLogin = System.currentTimeMillis(),
                                createdAt = System.currentTimeMillis()
                            )
                            firestore.collection("users").document(firebaseUser.uid).set(newUser).await()
                            _currentUser.value = newUser
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        // Offline fallback — safe defaults (EMPLOYEE, no admin escalation)
                        _currentUser.value = User(
                            uid = firebaseUser.uid,
                            name = firebaseUser.displayName ?: "",
                            email = firebaseUser.email ?: "",
                            photoUrl = firebaseUser.photoUrl?.toString(),
                            role = "EMPLOYEE",
                            active = false,
                            approvalStatus = "PENDING",
                            lastLogin = System.currentTimeMillis(),
                            createdAt = System.currentTimeMillis()
                        )
                    }
                }
            } else {
                _currentUser.value = null
            }
        }
    }

    override suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<User> {
        return try {
            val idToken = account.idToken ?: throw Exception("Google ID Token is missing")
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user ?: throw Exception("Firebase Auth failed")

            // 1. Fetch existing user record from Firestore
            val docRef = firestore.collection("users").document(firebaseUser.uid)
            val docSnapshot = docRef.get().await()

            val existingUser = if (docSnapshot.exists()) {
                docSnapshot.toObject(User::class.java)
            } else null

            // 2. Determine role & status from Firestore (NO hardcoded email checks)
            val email = firebaseUser.email ?: account.email ?: ""

            // For brand-new users, check if users collection is empty → first user = ADMIN
            val (role, approvalStatus, isActive) = if (existingUser != null) {
                Triple(existingUser.role, existingUser.approvalStatus, existingUser.active)
            } else {
                val usersSnapshot = firestore.collection("users").limit(1).get().await()
                val isFirstUser = usersSnapshot.isEmpty
                if (isFirstUser) {
                    Triple("ADMIN", "APPROVED", true)
                } else {
                    Triple("EMPLOYEE", "PENDING", false)
                }
            }

            // 3. Handle approval states
            when {
                approvalStatus == "REJECTED" -> {
                    // Allow sign-in but set state so UI shows rejected message
                    val user = User(
                        uid = firebaseUser.uid,
                        name = firebaseUser.displayName ?: account.displayName ?: "",
                        email = email,
                        photoUrl = firebaseUser.photoUrl?.toString() ?: account.photoUrl?.toString(),
                        role = role,
                        active = false,
                        approvalStatus = "REJECTED",
                        lastLogin = System.currentTimeMillis(),
                        createdAt = existingUser?.createdAt ?: System.currentTimeMillis()
                    )
                    _currentUser.value = user
                    Result.success(user)
                }
                approvalStatus == "APPROVED" && !isActive -> {
                    // Account disabled by admin
                    val user = User(
                        uid = firebaseUser.uid,
                        name = firebaseUser.displayName ?: account.displayName ?: "",
                        email = email,
                        photoUrl = firebaseUser.photoUrl?.toString() ?: account.photoUrl?.toString(),
                        role = role,
                        active = false,
                        approvalStatus = "APPROVED",
                        lastLogin = System.currentTimeMillis(),
                        createdAt = existingUser?.createdAt ?: System.currentTimeMillis()
                    )
                    _currentUser.value = user
                    Result.success(user)
                }
                else -> {
                    // PENDING or APPROVED+active — let them in, UI will gate access
                    val user = User(
                        uid = firebaseUser.uid,
                        name = firebaseUser.displayName ?: account.displayName ?: "",
                        email = email,
                        photoUrl = firebaseUser.photoUrl?.toString() ?: account.photoUrl?.toString(),
                        role = role,
                        active = isActive,
                        approvalStatus = approvalStatus,
                        lastLogin = System.currentTimeMillis(),
                        createdAt = existingUser?.createdAt ?: System.currentTimeMillis()
                    )

                    // Save/Update to Firestore
                    docRef.set(user).await()

                    _currentUser.value = user
                    Result.success(user)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun refreshCurrentUser() {
        val firebaseUser = firebaseAuth.currentUser ?: return
        try {
            val doc = firestore.collection("users").document(firebaseUser.uid).get().await()
            val u = doc.toObject(User::class.java)
            if (u != null) {
                _currentUser.value = u
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            _currentUser.value = null
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
