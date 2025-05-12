package com.example.trial_junior.feature_junior.presentation.viewModels.Invitation_Update

import com.example.trial_junior.feature_junior.domain.model.InvitationItem

data class InvitationNewUpdateState(
    val isChildNameHintVisible: Boolean = true,
    val isGuardianEmailHintVisible: Boolean = true,
    val isSpecialRequestsHintVisible: Boolean = true,
    val isAddressHintVisible: Boolean = true,
    val isDateHintVisible: Boolean = true,
    val isTimeHintVisible: Boolean = true,
    val isGuardianPhoneHintVisible: Boolean = true,
    val isAgeHintVisible: Boolean = true,
    val invitation: InvitationItem = InvitationItem(
        childName = "",
        guardianEmail = "",
        specialRequests = "",
        address = "",
        date = "",
        time = 0L,
        upcoming = true,
        approved = false,
        guardianPhone = 0L,
        age = 0,
        userId = 0, // Default value; server will set it
        id = null
    ),
    val isLoading: Boolean = true,
    val error: String? = null
)