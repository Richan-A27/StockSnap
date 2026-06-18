package com.stocksnap.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    @Update
    suspend fun update(product: Product)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getById(id: Long): Product?

    @Query("SELECT * FROM products ORDER BY createdAt DESC")
    suspend fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE DATE(createdAt / 1000, 'unixepoch') = DATE(:day / 1000, 'unixepoch') ORDER BY createdAt DESC")
    suspend fun getByDay(day: Long): List<Product>

    @Query("SELECT COUNT(*) FROM products WHERE status = :status")
    suspend fun countByStatus(status: com.stocksnap.domain.model.ProductStatus): Int

    @Query("SELECT * FROM products WHERE barcode = :barcode LIMIT 1")
    suspend fun findByBarcode(barcode: String): Product?
}
