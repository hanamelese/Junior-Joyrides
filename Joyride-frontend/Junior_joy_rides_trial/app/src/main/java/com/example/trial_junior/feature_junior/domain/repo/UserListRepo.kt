package com.example.trial_junior.feature_junior.domain.repo

import com.example.trial_junior.feature_junior.domain.model.UserItem

interface UserListRepo {
    suspend fun registerUser(firstName: String, lastName: String, email: String, password: String): UserItem
    suspend fun loginUser(email: String, password: String): String
    suspend fun getMyProfile(): UserItem
    suspend fun updateProfile(
        email: String,
        firstName: String?,
        lastName: String?,
        newEmail: String?,
        password: String?,
        profileImageUrl: String?,
        backgroundImageUrl: String?
    ): UserItem
}