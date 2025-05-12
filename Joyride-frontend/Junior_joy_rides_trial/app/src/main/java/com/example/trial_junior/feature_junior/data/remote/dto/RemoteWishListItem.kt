package com.example.trial_junior.feature_junior.data.remote.dto

data class RemoteWishListItem(
    val childName: String,
    val guardianEmail: String,
    val age: Int,
    val date: String,
    val specialRequests: String,
    val imageUrl: String,
    val upcoming: Boolean,
    val approved: Boolean,
    val id: Int? = null,
    val userId: Int? = null
)
