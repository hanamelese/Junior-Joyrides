package com.example.trial_junior.feature_junior.domain.model

data class SpecialInterviewItem(
    val childName: String,
    val guardianName: String,
    val guardianPhone: Long,
    val age: Int,
    val guardianEmail: String,
    val specialRequests: String,
    val videoUrl: String,
    override val upcoming: Boolean,
    override val approved: Boolean,
    override val id: Int? = null,
    val userId: Int? = null
) : ListItem