package com.stocksnap.data.repository

import com.stocksnap.data.database.Product
import com.stocksnap.data.database.ProductDao

class ProductRepositoryImpl(
    private val dao: ProductDao
) : ProductRepository {
    override suspend fun insert(product: Product): Long {
        return dao.insert(product)
    }

    override suspend fun update(product: Product) {
        dao.update(product)
    }

    override suspend fun getById(id: Long): Product? {
        return dao.getById(id)
    }

    override suspend fun getAll(): List<Product> {
        return dao.getAll()
    }

    override suspend fun getByDay(day: Long): List<Product> {
        return dao.getByDay(day)
    }

    override suspend fun findByBarcode(barcode: String): Product? {
        return dao.findByBarcode(barcode)
    }
}
