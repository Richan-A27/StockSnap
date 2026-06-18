package com.stocksnap.data.repository

import com.stocksnap.data.database.Product

interface ProductRepository {
    suspend fun insert(product: Product): Long
    suspend fun update(product: Product)
    suspend fun getById(id: Long): Product?
    suspend fun getAll(): List<Product>
    suspend fun getByDay(day: Long): List<Product>
    suspend fun findByBarcode(barcode: String): Product?
}
