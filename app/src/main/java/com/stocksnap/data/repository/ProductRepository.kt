package com.stocksnap.data.repository

import com.stocksnap.data.database.Product
import com.stocksnap.data.model.ActivityLog
import com.stocksnap.data.model.Arrival
import com.stocksnap.data.model.ScanSession

interface ProductRepository {
    // Products Catalog
    suspend fun insertProduct(product: Product): Long
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(id: Long)
    suspend fun getProductById(id: Long): Product?
    suspend fun getProductByBarcode(barcode: String): Product?
    suspend fun getProductByBarcodeFirestore(barcode: String): Product?
    suspend fun getAllProducts(): List<Product>
    
    // Arrivals Deliveries
    suspend fun insertArrival(arrival: Arrival): Long
    suspend fun updateArrival(arrival: Arrival)
    suspend fun deleteArrival(id: Long)
    suspend fun getArrivalById(id: Long): Arrival?
    suspend fun getArrivalByArrivalId(arrivalId: String): Arrival?
    suspend fun getAllArrivals(): List<Arrival>
    suspend fun getArrivalsByDay(day: Long): List<Arrival>
    suspend fun getArrivalsForToday(barcode: String, mrp: Double): List<Arrival>
    
    // Scan Sessions (local operational history)
    suspend fun getAllScanSessions(): List<ScanSession>
    suspend fun logAction(barcode: String, productName: String, action: String)
    
    // Activity Feed (shared collaborative feed)
    suspend fun logActivityFeed(actionType: String, barcode: String, productName: String)
    suspend fun getLatestActivityLogs(limit: Int = 100): List<ActivityLog>
    
    // Live Synchronisation
    fun startRealtimeSync()
    fun stopRealtimeSync()
    fun triggerSync()

    // Central User Management
    suspend fun getAllUsers(): List<com.stocksnap.data.model.User>
    suspend fun updateUserActiveStatus(uid: String, name: String, isActive: Boolean)
    suspend fun getArrivalsCountByUser(uid: String): Int
    
    // Approval Workflow
    suspend fun approveUser(uid: String, name: String)
    suspend fun rejectUser(uid: String, name: String)
    suspend fun disableUser(uid: String, name: String)
    suspend fun enableUser(uid: String, name: String)
}
