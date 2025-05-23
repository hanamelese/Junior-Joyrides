package com.example.joyrides.feature_junior.data.remote.dto

data class RemoteBasicInterviewItem(
    val childName: String,
    val guardianName: String,
    val guardianPhone: Long,
    val age: Int,
    val guardianEmail: String,
    val specialRequests: String,
    val upcoming: Boolean,
    val approved: Boolean,
    val id: Int? = null,
    val userId: Int? = null
)
