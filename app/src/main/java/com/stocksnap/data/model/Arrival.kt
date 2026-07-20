package com.stocksnap.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksnap.domain.model.ProductStatus

@Entity(tableName = "arrivals")
data class Arrival(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val arrivalId: String = "",
    val barcode: String = "",
    val productName: String = "",
    val mrp: Double = 0.0,
    val quantityReceived: Int = 0,
    val status: ProductStatus = ProductStatus.PENDING,
    val createdByUid: String = "",
    val createdByName: String = "",
    val createdByEmail: String = "",
    val updatedByUid: String? = null,
    val updatedByName: String? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val optionalSupplierName: String? = null,
    val isPendingSync: Int = 0
) {
    // No-arg constructor for Firestore
    constructor() : this(
        id = 0,
        arrivalId = "",
        barcode = "",
        productName = "",
        mrp = 0.0,
        quantityReceived = 0,
        status = ProductStatus.PENDING,
        createdByUid = "",
        createdByName = "",
        createdByEmail = "",
        updatedByUid = null,
        updatedByName = null,
        createdAt = 0L,
        updatedAt = 0L,
        optionalSupplierName = null,
        isPendingSync = 0
    )

    fun toFirestoreMap(): Map<String, Any?> {
        return mapOf(
            "arrivalId" to arrivalId,
            "barcode" to barcode,
            "productName" to productName,
            "mrp" to mrp,
            "quantityReceived" to quantityReceived,
            "status" to status.name,
            "createdByUid" to createdByUid,
            "createdByName" to createdByName,
            "createdByEmail" to createdByEmail,
            "updatedByUid" to updatedByUid,
            "updatedByName" to updatedByName,
            "createdAt" to createdAt,
            "updatedAt" to updatedAt,
            "optionalSupplierName" to optionalSupplierName
        )
    }
}
