package com.example.trial_junior.feature_junior.data.remote.dto

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)