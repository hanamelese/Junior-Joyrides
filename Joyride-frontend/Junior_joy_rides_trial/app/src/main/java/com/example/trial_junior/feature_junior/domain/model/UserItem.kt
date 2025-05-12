package com.example.trial_junior.feature_junior.domain.model

data class UserItem(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: String,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val invitations: List<InvitationItem>? = emptyList(),
    val basicInterviews: List<BasicInterviewItem>? = emptyList(),
    val specialInterviews: List<SpecialInterviewItem>? = emptyList(),
    val wishLists: List<WishListItem>? = emptyList()
)