package com.stocksnap.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stocksnap.data.model.Arrival
import com.stocksnap.data.model.ScanSession
import com.stocksnap.data.model.ActivityLog
import com.stocksnap.domain.model.ProductStatus

@Dao
interface ProductDao {
    // Products
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    @Update
    suspend fun update(product: Product)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProductById(id: Long)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getById(id: Long): Product?

    @Query("SELECT * FROM products WHERE barcode = :barcode LIMIT 1")
    suspend fun findByBarcode(barcode: String): Product?

    @Query("SELECT * FROM products ORDER BY createdAt DESC")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products ORDER BY createdAt DESC")
    fun getAllProductsFlow(): kotlinx.coroutines.flow.Flow<List<Product>>

    @Query("SELECT * FROM products WHERE isPendingSync = 1")
    suspend fun getUnsyncedProducts(): List<Product>

    @Query("UPDATE products SET isPendingSync = 0 WHERE id = :id")
    suspend fun markProductSynced(id: Long)

    @Query("UPDATE products SET isPendingSync = 0, frontImageUrl = :imageUrl WHERE id = :id")
    suspend fun markProductSyncedWithUrl(id: Long, imageUrl: String)


    // Arrivals
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArrival(arrival: Arrival): Long

    @Update
    suspend fun updateArrival(arrival: Arrival)

    @Query("DELETE FROM arrivals WHERE id = :id")
    suspend fun deleteArrivalById(id: Long)

    @Query("DELETE FROM arrivals WHERE arrivalId = :arrivalId")
    suspend fun deleteArrivalByArrivalId(arrivalId: String)

    @Query("SELECT * FROM arrivals WHERE id = :id")
    suspend fun getArrivalById(id: Long): Arrival?

    @Query("SELECT * FROM arrivals WHERE arrivalId = :arrivalId LIMIT 1")
    suspend fun getArrivalByArrivalId(arrivalId: String): Arrival?

    @Query("SELECT * FROM arrivals ORDER BY createdAt DESC")
    suspend fun getAllArrivals(): List<Arrival>

    @Query("SELECT * FROM arrivals ORDER BY createdAt DESC")
    fun getAllArrivalsFlow(): kotlinx.coroutines.flow.Flow<List<Arrival>>

    @Query("SELECT * FROM arrivals WHERE DATE(createdAt / 1000, 'unixepoch') = DATE(:day / 1000, 'unixepoch') ORDER BY createdAt DESC")
    suspend fun getArrivalsByDay(day: Long): List<Arrival>

    @Query("SELECT * FROM arrivals WHERE barcode = :barcode AND mrp = :mrp AND createdAt >= :startOfDay AND createdAt <= :endOfDay ORDER BY createdAt DESC")
    suspend fun getArrivalsForToday(barcode: String, mrp: Double, startOfDay: Long, endOfDay: Long): List<Arrival>


    @Query("SELECT COUNT(*) FROM arrivals WHERE status = :status")
    suspend fun countArrivalsByStatus(status: ProductStatus): Int

    @Query("SELECT * FROM arrivals WHERE isPendingSync = 1")
    suspend fun getUnsyncedArrivals(): List<Arrival>

    @Query("UPDATE arrivals SET isPendingSync = 0 WHERE id = :id")
    suspend fun markArrivalSynced(id: Long)


    // Scan Sessions (local operational history — kept)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScanSession(session: ScanSession): Long

    @Query("SELECT * FROM scan_sessions WHERE isPendingSync = 1")
    suspend fun getUnsyncedScanSessions(): List<ScanSession>

    @Query("UPDATE scan_sessions SET isPendingSync = 0 WHERE id = :id")
    suspend fun markScanSessionSynced(id: Long)

    @Query("SELECT * FROM scan_sessions ORDER BY timestamp DESC")
    suspend fun getAllScanSessions(): List<ScanSession>


    // Activity Logs (shared collaborative feed)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLog(log: ActivityLog): Long

    @Query("SELECT * FROM activity_logs WHERE isPendingSync = 1")
    suspend fun getUnsyncedActivityLogs(): List<ActivityLog>

    @Query("UPDATE activity_logs SET isPendingSync = 0 WHERE id = :id")
    suspend fun markActivityLogSynced(id: Long)

    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getLatestActivityLogs(limit: Int = 100): List<ActivityLog>

    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT :limit")
    fun getLatestActivityLogsFlow(limit: Int = 100): kotlinx.coroutines.flow.Flow<List<ActivityLog>>
}
