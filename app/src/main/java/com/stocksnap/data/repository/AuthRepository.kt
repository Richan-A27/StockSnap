package com.stocksnap.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.stocksnap.data.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: StateFlow<User?>
    suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<User>
    suspend fun signOut(): Result<Unit>
    suspend fun refreshCurrentUser()
}
