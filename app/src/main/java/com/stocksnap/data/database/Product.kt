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

    val mrp: Double = 0.0,

    val brand: String? = null,

    val quantity: Int? = null,

    val frontImagePath: String = "",

    val barcodeImagePath: String = "",

    val mrpImagePath: String = "",
    
    val thumbnailPath: String? = null,
    
    val status: ProductStatus = ProductStatus.PENDING,

    val createdAt: Long,

    val updatedAt: Long,

    // New Fields for separated catalog
    val weight: String = "",
    val frontImageUrl: String? = null,
    val catalogImagePath: String? = null,
    val isPendingSync: Int = 0,
    val code: String = ""
) {
    // No-arg constructor for Firestore
    constructor() : this(
        id = 0L,
        name = "",
        barcode = "",
        mrp = 0.0,
        brand = null,
        quantity = null,
        frontImagePath = "",
        barcodeImagePath = "",
        mrpImagePath = "",
        thumbnailPath = null,
        status = ProductStatus.PENDING,
        createdAt = 0L,
        updatedAt = 0L,
        weight = "",
        frontImageUrl = null,
        catalogImagePath = null,
        isPendingSync = 0,
        code = ""
    )

    fun toFirestoreMap(): Map<String, Any?> {
        return mapOf(
            "barcode" to barcode,
            "name" to name,
            "weight" to weight,
            "frontImageUrl" to frontImageUrl,
            "createdAt" to createdAt,
            "updatedAt" to updatedAt,
            "code" to code
        )
    }
}
