package com.example.trial_junior.feature_junior.domain.use_case

import com.example.trial_junior.feature_junior.domain.model.UserItem
import com.example.trial_junior.feature_junior.domain.repo.UserListRepo
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    private val repository: UserListRepo
) {
    suspend fun registerUser(firstName: String, lastName: String, email: String, password: String): UserItem =
        repository.registerUser(firstName, lastName, email, password)

    suspend fun loginUser(email: String, password: String): String =
        repository.loginUser(email, password)

    suspend fun getMyProfile(): UserItem =
        repository.getMyProfile()

    suspend fun updateProfile(
        email: String,
        firstName: String?,
        lastName: String?,
        newEmail: String?,
        password: String?,
        profileImageUrl: String?,
        backgroundImageUrl: String?
    ): UserItem {
        return repository.updateProfile(
            email,
            firstName,
            lastName,
            newEmail,
            password,
            profileImageUrl,
            backgroundImageUrl
        )
    }}