package com.stocksnap.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val role: String = "EMPLOYEE",
    val active: Boolean = false,
    val approvalStatus: String = "PENDING",
    val lastLogin: Long = 0L,
    val createdAt: Long = 0L
)
