package com.stocksnap.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksnap.domain.model.ProductStatus

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val barcode: String,

    val mrp: Double,

    val brand: String?,

    val quantity: Int?,

    val frontImagePath: String,

    val barcodeImagePath: String,

    val mrpImagePath: String,
    val thumbnailPath: String? = null,
    val status: ProductStatus,

    val createdAt: Long,

    val updatedAt: Long
)
