package com.example.trial_junior.feature_junior.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basic_interview")
data class LocalBasicInterviewItem(
    val childName: String,
    val guardianName: String,
    val guardianPhone: Long,
    val age: Int,
    val guardianEmail: String,
    val specialRequests: String,
    val upcoming: Boolean,
    val approved: Boolean,
    @PrimaryKey
    val id: Int,
    val userId: Int?
)
