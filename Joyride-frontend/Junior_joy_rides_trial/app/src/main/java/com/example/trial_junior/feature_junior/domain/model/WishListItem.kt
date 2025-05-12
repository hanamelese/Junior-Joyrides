package com.example.trial_junior.feature_junior.domain.model

import com.example.trial_junior.feature_junior.domain.model.ListItem

data class WishListItem(
    val childName: String,
    val guardianEmail: String,
    val age: Int,
    val date: String,
    val specialRequests: String,
    val imageUrl: String,
    override val upcoming: Boolean,
    override val approved: Boolean,
    override val id: Int? = null,
    val userId: Int? = null
) : ListItem
