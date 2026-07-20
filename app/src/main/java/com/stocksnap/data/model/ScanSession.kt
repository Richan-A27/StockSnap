package com.stocksnap.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_sessions")
data class ScanSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val employeeUid: String = "",
    val employeeName: String = "",
    val timestamp: Long = 0L,
    val barcode: String = "",
    val productName: String = "",
    val action: String = "",
    val isPendingSync: Int = 0
) {
    // No-arg constructor for Firestore
    constructor() : this(0, "", "", 0L, "", "", "", 0)

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "employeeUid" to employeeUid,
            "employeeName" to employeeName,
            "timestamp" to timestamp,
            "barcode" to barcode,
            "productName" to productName,
            "action" to action
        )
    }
}
