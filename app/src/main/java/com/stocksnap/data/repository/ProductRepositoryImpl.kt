package com.stocksnap.data.repository

import android.net.Uri
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.stocksnap.data.database.Product
import com.stocksnap.data.database.ProductDao
import com.stocksnap.data.model.ActivityLog
import com.stocksnap.data.model.Arrival
import com.stocksnap.data.model.ScanSession
import com.stocksnap.data.model.User
import com.stocksnap.data.sync.SyncWorker
import com.stocksnap.domain.model.ProductStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val workManager: WorkManager
) : ProductRepository {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var productsListener: ListenerRegistration? = null
    private var arrivalsListener: ListenerRegistration? = null
    private var activityListener: ListenerRegistration? = null

    // PRODUCTS MASTER CATALOG
    override suspend fun insertProduct(product: Product): Long {
        val toSave = product.copy(isPendingSync = 1)
        val id = dao.insert(toSave)
        logAction(product.barcode, product.name, "Created Catalog")
        logActivityFeed("PRODUCT_CREATED", product.barcode, product.name)
        triggerSync()
        return id
    }

    override suspend fun updateProduct(product: Product) {
        val toSave = product.copy(isPendingSync = 1)
        dao.update(toSave)
        logAction(product.barcode, product.name, "Updated Catalog")
        logActivityFeed("PRODUCT_UPDATED", product.barcode, product.name)
        triggerSync()
    }

    override suspend fun deleteProduct(id: Long) {
        val local = dao.getById(id) ?: return
        dao.deleteProductById(id)
        deleteProductLocalFiles(local)
        logAction(local.barcode, local.name, "Deleted Catalog")
        logActivityFeed("PRODUCT_DELETED", local.barcode, local.name)
        
        scope.launch {
            try {
                firestore.collection("products").document(local.barcode).delete().await()
                try {
                    storage.reference.child("products/${local.barcode}/front.jpg").delete().await()
                } catch (_: Exception) {}
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun getProductById(id: Long): Product? {
        return dao.getById(id)
    }

    override suspend fun getProductByBarcode(barcode: String): Product? {
        return dao.findByBarcode(barcode)
    }

    override suspend fun getProductByBarcodeFirestore(barcode: String): Product? {
        return try {
            val doc = firestore.collection("products").document(barcode).get().await()
            if (doc.exists()) {
                val fProduct = doc.toObject(Product::class.java)
                if (fProduct != null) {
                    val local = dao.findByBarcode(barcode)
                    val toCache = if (local != null) {
                        local.copy(
                            name = fProduct.name,
                            weight = fProduct.weight,
                            frontImageUrl = fProduct.frontImageUrl,
                            createdAt = fProduct.createdAt,
                            updatedAt = fProduct.updatedAt,
                            isPendingSync = 0
                        )
                    } else {
                        fProduct.copy(isPendingSync = 0)
                    }
                    dao.insert(toCache)
                    toCache
                } else null
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getAllProducts(): List<Product> {
        return dao.getAllProducts()
    }

    override fun getAllProductsFlow(): kotlinx.coroutines.flow.Flow<List<Product>> {
        return dao.getAllProductsFlow()
    }


    // ARRIVALS DELIVERIES
    override suspend fun insertArrival(arrival: Arrival): Long {
        val employee = firebaseAuth.currentUser
        val updated = arrival.copy(
            createdByUid = employee?.uid ?: "unknown_uid",
            createdByName = employee?.displayName ?: "unknown_name",
            createdByEmail = employee?.email ?: "unknown_email",
            isPendingSync = 1
        )
        val id = dao.insertArrival(updated)
        logAction(arrival.barcode, arrival.productName, "Created Arrival")
        logActivityFeed("ARRIVAL_CREATED", arrival.barcode, arrival.productName)
        triggerSync()
        return id
    }

    override suspend fun updateArrival(arrival: Arrival) {
        val employee = firebaseAuth.currentUser
        val updated = arrival.copy(
            updatedByUid = employee?.uid ?: "unknown_uid",
            updatedByName = employee?.displayName ?: "unknown_name",
            isPendingSync = 1
        )
        dao.updateArrival(updated)
        
        val actionType = if (arrival.status == ProductStatus.UPDATED) "PRODUCT_UPDATED" else "PRODUCT_MARKED_PENDING"
        val actionLog = if (arrival.status == ProductStatus.UPDATED) "Updated" else "Marked Pending"
        logAction(arrival.barcode, arrival.productName, actionLog)
        logActivityFeed(actionType, arrival.barcode, arrival.productName)
        triggerSync()
    }

    override suspend fun deleteArrival(id: Long) {
        val local = dao.getArrivalById(id) ?: return
        dao.deleteArrivalById(id)
        logAction(local.barcode, local.productName, "Deleted Arrival")
        logActivityFeed("ARRIVAL_DELETED", local.barcode, local.productName)
        
        scope.launch {
            try {
                firestore.collection("arrivals").document(local.arrivalId).delete().await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun getArrivalById(id: Long): Arrival? {
        return dao.getArrivalById(id)
    }

    override suspend fun getArrivalByArrivalId(arrivalId: String): Arrival? {
        return dao.getArrivalByArrivalId(arrivalId)
    }

    override suspend fun getAllArrivals(): List<Arrival> {
        return dao.getAllArrivals()
    }

    override fun getAllArrivalsFlow(): kotlinx.coroutines.flow.Flow<List<Arrival>> {
        return dao.getAllArrivalsFlow()
    }

    override suspend fun getArrivalsByDay(day: Long): List<Arrival> {
        return dao.getArrivalsByDay(day)
    }

    override suspend fun getArrivalsForToday(barcode: String, mrp: Double): List<Arrival> {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        calendar.set(java.util.Calendar.HOUR_OF_DAY, 23)
        calendar.set(java.util.Calendar.MINUTE, 59)
        calendar.set(java.util.Calendar.SECOND, 59)
        calendar.set(java.util.Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        return dao.getArrivalsForToday(barcode, mrp, startOfDay, endOfDay)
    }


    // SCAN SESSIONS (local operational history — kept as-is)
    override suspend fun getAllScanSessions(): List<ScanSession> {
        return dao.getAllScanSessions()
    }

    override suspend fun logAction(barcode: String, productName: String, action: String) {
        val employee = firebaseAuth.currentUser
        val session = ScanSession(
            employeeUid = employee?.uid ?: "unknown_uid",
            employeeName = employee?.displayName ?: "unknown_name",
            timestamp = System.currentTimeMillis(),
            barcode = barcode,
            productName = productName,
            action = action,
            isPendingSync = 1
        )
        dao.insertScanSession(session)
    }


    // ACTIVITY FEED (shared collaborative feed)
    override suspend fun logActivityFeed(actionType: String, barcode: String, productName: String) {
        val employee = firebaseAuth.currentUser
        val log = ActivityLog(
            activityId = UUID.randomUUID().toString(),
            performedByUid = employee?.uid ?: "unknown_uid",
            performedByName = employee?.displayName ?: "Unknown",
            performedByEmail = employee?.email ?: "unknown@email.com",
            actionType = actionType,
            productName = productName,
            barcode = barcode,
            timestamp = System.currentTimeMillis(),
            isPendingSync = 1
        )
        dao.insertActivityLog(log)
    }

    override suspend fun getLatestActivityLogs(limit: Int): List<ActivityLog> {
        return dao.getLatestActivityLogs(limit)
    }

    override fun getLatestActivityLogsFlow(limit: Int): kotlinx.coroutines.flow.Flow<List<ActivityLog>> {
        return dao.getLatestActivityLogsFlow(limit)
    }


    // SYNC MANAGEMENT
    override fun startRealtimeSync() {
        // Products listener
        if (productsListener == null) {
            productsListener = firestore.collection("products")
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        e.printStackTrace()
                        return@addSnapshotListener
                    }
                    if (snapshots != null) {
                        scope.launch {
                            for (change in snapshots.documentChanges) {
                                val remote = change.document.toObject(Product::class.java)
                                val barcode = change.document.id
                                val local = dao.findByBarcode(barcode)
                                when (change.type) {
                                    DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED -> {
                                        val merged = if (local != null) {
                                            local.copy(
                                                name = remote.name,
                                                weight = remote.weight,
                                                frontImageUrl = remote.frontImageUrl,
                                                createdAt = remote.createdAt,
                                                updatedAt = remote.updatedAt,
                                                isPendingSync = 0
                                            )
                                        } else {
                                            remote.copy(isPendingSync = 0)
                                        }
                                        dao.insert(merged)
                                    }
                                    DocumentChange.Type.REMOVED -> {
                                        if (local != null) {
                                            dao.deleteProductById(local.id)
                                            deleteProductLocalFiles(local)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }

        // Arrivals listener
        if (arrivalsListener == null) {
            arrivalsListener = firestore.collection("arrivals")
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        e.printStackTrace()
                        return@addSnapshotListener
                    }
                    if (snapshots != null) {
                        scope.launch {
                            for (change in snapshots.documentChanges) {
                                val remote = change.document.toObject(Arrival::class.java)
                                val arrivalId = change.document.id
                                val local = dao.getArrivalByArrivalId(arrivalId)
                                when (change.type) {
                                    DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED -> {
                                        val merged = if (local != null) {
                                            local.copy(
                                                barcode = remote.barcode,
                                                productName = remote.productName,
                                                mrp = remote.mrp,
                                                quantityReceived = remote.quantityReceived,
                                                status = remote.status,
                                                createdByUid = remote.createdByUid,
                                                createdByName = remote.createdByName,
                                                createdByEmail = remote.createdByEmail,
                                                updatedByUid = remote.updatedByUid,
                                                updatedByName = remote.updatedByName,
                                                createdAt = remote.createdAt,
                                                updatedAt = remote.updatedAt,
                                                optionalSupplierName = remote.optionalSupplierName,
                                                isPendingSync = 0
                                            )
                                        } else {
                                            remote.copy(isPendingSync = 0)
                                        }
                                        dao.insertArrival(merged)
                                    }
                                    DocumentChange.Type.REMOVED -> {
                                        if (local != null) {
                                            dao.deleteArrivalById(local.id)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }

        // Activity Logs listener (real-time shared feed across all devices)
        if (activityListener == null) {
            activityListener = firestore.collection("activity_logs")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(100)
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        e.printStackTrace()
                        return@addSnapshotListener
                    }
                    if (snapshots != null) {
                        scope.launch {
                            for (change in snapshots.documentChanges) {
                                if (change.type == DocumentChange.Type.ADDED) {
                                    val remote = change.document.toObject(ActivityLog::class.java)
                                    val activityId = change.document.id
                                    // Only insert if we don't already have it locally
                                    val existing = dao.getLatestActivityLogs(200)
                                        .find { it.activityId == activityId }
                                    if (existing == null) {
                                        dao.insertActivityLog(
                                            remote.copy(
                                                activityId = activityId,
                                                isPendingSync = 0
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }

    override fun stopRealtimeSync() {
        productsListener?.remove()
        productsListener = null
        arrivalsListener?.remove()
        arrivalsListener = null
        activityListener?.remove()
        activityListener = null
    }

    override fun triggerSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()
        workManager.enqueueUniqueWork("StockSnapSync", ExistingWorkPolicy.REPLACE, syncRequest)
    }

    private fun deleteProductLocalFiles(product: Product) {
        listOf(product.frontImagePath, product.barcodeImagePath, product.mrpImagePath, product.thumbnailPath, product.catalogImagePath)
            .filterNotNull()
            .filter { it.isNotEmpty() }
            .forEach { path ->
                try {
                    val file = File(path)
                    if (file.exists()) file.delete()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }

    override suspend fun getAllUsers(): List<User> = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        try {
            firestore.collection("users")
                .get()
                .await()
                .toObjects(User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun updateUserActiveStatus(uid: String, name: String, isActive: Boolean) {
        try {
            // Update active and isActive in Firestore
            val docRef = firestore.collection("users").document(uid)
            docRef.update("active", isActive).await()

            // Log activity in activity_logs
            val currentAdmin = firebaseAuth.currentUser?.displayName ?: "Admin"
            val action = if (isActive) "enabled" else "disabled"
            logActivityFeed(
                actionType = "EMPLOYEE_${action.uppercase(java.util.Locale.getDefault())}",
                barcode = uid,
                productName = "$currentAdmin $action $name"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getArrivalsCountByUser(uid: String): Int {
        return dao.getAllArrivals().count { it.createdByUid == uid }
    }

    override suspend fun approveUser(uid: String, name: String) {
        try {
            val docRef = firestore.collection("users").document(uid)
            docRef.update(
                mapOf(
                    "approvalStatus" to "APPROVED",
                    "active" to true
                )
            ).await()

            val currentAdmin = firebaseAuth.currentUser?.displayName ?: "Admin"
            logActivityFeed(
                actionType = "USER_APPROVED",
                barcode = uid,
                productName = "$currentAdmin approved $name"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun rejectUser(uid: String, name: String) {
        try {
            val docRef = firestore.collection("users").document(uid)
            docRef.update(
                mapOf(
                    "approvalStatus" to "REJECTED",
                    "active" to false
                )
            ).await()

            val currentAdmin = firebaseAuth.currentUser?.displayName ?: "Admin"
            logActivityFeed(
                actionType = "USER_REJECTED",
                barcode = uid,
                productName = "$currentAdmin rejected $name"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun disableUser(uid: String, name: String) {
        try {
            val docRef = firestore.collection("users").document(uid)
            docRef.update("active", false).await()

            val currentAdmin = firebaseAuth.currentUser?.displayName ?: "Admin"
            logActivityFeed(
                actionType = "USER_DISABLED",
                barcode = uid,
                productName = "$currentAdmin disabled $name"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun enableUser(uid: String, name: String) {
        try {
            val docRef = firestore.collection("users").document(uid)
            docRef.update("active", true).await()

            val currentAdmin = firebaseAuth.currentUser?.displayName ?: "Admin"
            logActivityFeed(
                actionType = "USER_ENABLED",
                barcode = uid,
                productName = "$currentAdmin enabled $name"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
