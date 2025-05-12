package com.example.trial_junior.feature_junior.data.remote.dto

data class RemoteInvitationItem(
    val childName: String,
    val guardianEmail: String,
    val specialRequests: String,
    val address: String,
    val date: String,
    val time: Long,
    val upcoming: Boolean,
    val approved: Boolean,
    val guardianPhone: Long,
    val age: Int,
    val userId: Int? = null,
    val id: Int? = null
)
