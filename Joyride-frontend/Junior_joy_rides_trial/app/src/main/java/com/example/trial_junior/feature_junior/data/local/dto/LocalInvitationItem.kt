package com.example.trial_junior.feature_junior.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invitation")
data class LocalInvitationItem(
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
    val userId: Int,
    @PrimaryKey
    val id: Int
)
