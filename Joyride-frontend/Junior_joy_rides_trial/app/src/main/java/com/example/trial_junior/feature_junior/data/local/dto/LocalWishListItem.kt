package com.example.trial_junior.feature_junior.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish_list")
data class LocalWishListItem(
    val childName: String,
    val guardianEmail: String,
    val age: Int,
    val date: String,
    val specialRequests: String,
    val imageUrl: String,
    val upcoming: Boolean,
    val approved: Boolean,
    @PrimaryKey val id: Int,
    val userId: Int?
)
