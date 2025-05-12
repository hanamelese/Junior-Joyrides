package com.example.trial_junior.feature_junior.presentation.viewModels.WishList_Update

import com.example.trial_junior.feature_junior.domain.model.WishListItem
import kotlin.String

data class WishListNewUpdateState(
    val isChildNameHintVisible: Boolean = true,
    val isAgeHintVisible: Boolean = true,
    val isDateHintVisible: Boolean = true,
    val isGuardianEmailHintVisible: Boolean = true,
    val isImageUrlHintVisible: Boolean = true,
    val isSpecialRequestsHintVisible: Boolean = true,
    val wishlist: WishListItem = WishListItem(
        childName = "",
        guardianEmail = "",
        age = 0,
        date = "",
        specialRequests = "",
        imageUrl = "",
        upcoming = true,
        approved = false,
        id = null,
        userId = 0   // Default value; server will assign the actual userId
    ),
    val isLoading: Boolean = true,
    val error: String? = null
)
