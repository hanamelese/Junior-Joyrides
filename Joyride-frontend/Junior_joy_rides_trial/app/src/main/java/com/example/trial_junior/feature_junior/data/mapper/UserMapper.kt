package com.example.trial_junior.feature_junior.data.mapper

import com.example.trial_junior.feature_junior.data.local.dto.LocalUserItem
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteUserItem
import com.example.trial_junior.feature_junior.domain.model.UserItem


fun UserItem.toLocalUserItem(id: Int): LocalUserItem {
    return LocalUserItem(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = "", // Not storing password
        salt = "",
        role = role,
        profileImageUrl = profileImageUrl,
        backgroundImageUrl = backgroundImageUrl
    )
}

fun RemoteUserItem.toUserItem(): UserItem {
    return UserItem(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        role = role,
        profileImageUrl = profileImageUrl,
        backgroundImageUrl = backgroundImageUrl,
        invitations = invitations?.map { it.toInvitationItem() } ?: emptyList(),
        basicInterviews = basicInterviews?.map { it.toBasicInterviewItem() } ?: emptyList(),
        specialInterviews = specialInterviews?.map { it.toSpecialInterviewItem() } ?: emptyList(),
        wishLists = wishLists?.map { it.toWishListItem() } ?: emptyList()
    )
}

fun LocalUserItem.toUserItem(): UserItem {
    return UserItem(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        role = role,
        profileImageUrl = profileImageUrl,
        backgroundImageUrl = backgroundImageUrl,
        invitations = emptyList(), // Relations not stored locally
        basicInterviews = emptyList(),
        specialInterviews = emptyList(),
        wishLists = emptyList()
    )
}