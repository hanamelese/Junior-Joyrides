package com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterview_Update

import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem

data class SpecialInterviewNewUpdateState(
    val isChildNameHintVisible: Boolean = true,
    val isGuardianNameHintVisible: Boolean = true,
    val isGuardianPhoneHintVisible: Boolean = true,
    val isAgeHintVisible: Boolean = true,
    val isGuardianEmailHintVisible: Boolean = true,
    val isSpecialRequestsHintVisible: Boolean = true,
    val isVideoUrlHintVisible: Boolean = true,
    val specialInterview: SpecialInterviewItem = SpecialInterviewItem(
        childName = "",
        guardianName = "",
        guardianPhone = 0L,
        age = 0,
        guardianEmail = "",
        specialRequests = "",
        videoUrl = "",
        upcoming = true,
        approved = false,
        id = null,
        userId = 0 // Default value; server will assign the actual userId
    ),
    val isLoading: Boolean = true,
    val error: String? = null
)