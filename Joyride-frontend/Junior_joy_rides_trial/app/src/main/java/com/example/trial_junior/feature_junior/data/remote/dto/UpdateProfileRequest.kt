package com.example.trial_junior.feature_junior.data.remote.dto

data class UpdateProfileRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val salt: String? = null,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
)