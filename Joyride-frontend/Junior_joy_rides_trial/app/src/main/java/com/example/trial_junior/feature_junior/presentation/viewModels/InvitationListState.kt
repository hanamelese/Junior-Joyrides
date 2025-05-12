package com.example.trial_junior.feature_junior.presentation.viewModels

import com.example.trial_junior.feature_junior.domain.model.InvitationItem
import com.example.trial_junior.feature_junior.domain.util.InvitationItemOrder
import com.example.trial_junior.feature_junior.domain.util.SortingDirection

data class InvitationListState(
    val items: List<InvitationItem> = emptyList(),
    val invitationItemOrder: InvitationItemOrder = InvitationItemOrder.Time(SortingDirection.Down, true, true),
    val isLoading: Boolean = true,
    val error: String? = null,
    val showHosted: Boolean = true,
    val showApproved: Boolean = true,
)