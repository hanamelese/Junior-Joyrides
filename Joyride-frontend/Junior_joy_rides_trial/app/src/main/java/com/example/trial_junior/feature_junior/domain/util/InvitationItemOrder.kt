package com.example.trial_junior.feature_junior.domain.util

sealed class InvitationItemOrder(
    val sortingDirection: SortingDirection,
    val showHosted: Boolean,
    val showApproved: Boolean
) {
    class Time(sortingDirection: SortingDirection, showHosted: Boolean, showApproved: Boolean): InvitationItemOrder(sortingDirection,showHosted, showApproved)

    fun copy( sortingDirection: SortingDirection, showHosted: Boolean, showApproved: Boolean): InvitationItemOrder{
        return when(this){
            is Time -> Time(sortingDirection, showHosted, showApproved)
        }
    }

}