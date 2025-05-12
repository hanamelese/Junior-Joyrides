package com.example.trial_junior.feature_junior.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class LocalUserItem(
    @PrimaryKey
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val salt: String,
    val role: String,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null
)