package com.example.trial_junior.feature_junior.data.remote.dto

data class RemoteSpecialInterviewItem(
    val childName: String,
    val guardianName: String,
    val guardianPhone: Long,
    val age: Int,
    val guardianEmail: String,
    val specialRequests: String,
    val videoUrl: String,
    val upcoming: Boolean,
    val approved: Boolean,
    val id: Int? = null,
    val userId: Int? = null
)
