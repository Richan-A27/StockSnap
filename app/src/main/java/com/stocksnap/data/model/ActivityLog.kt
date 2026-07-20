package com.stocksnap.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_logs")
data class ActivityLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val activityId: String = "",
    val performedByUid: String = "",
    val performedByName: String = "",
    val performedByEmail: String = "",
    val actionType: String = "",
    val productName: String = "",
    val barcode: String = "",
    val timestamp: Long = 0L,
    val isPendingSync: Int = 0
) {
    constructor() : this(0, "", "", "", "", "", "", "", 0L, 0)

    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "activityId"       to activityId,
        "performedByUid"   to performedByUid,
        "performedByName"  to performedByName,
        "performedByEmail" to performedByEmail,
        "actionType"       to actionType,
        "productName"      to productName,
        "barcode"          to barcode,
        "timestamp"        to timestamp
    )
}
