package com.example.trial_junior.feature_junior.presentation.components

import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import com.example.trial_junior.core.util.ListStrings
import com.example.trial_junior.feature_junior.domain.util.InvitationItemOrder
import com.example.trial_junior.feature_junior.domain.util.SortingDirection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingDrawerOptions(
    invitationItemOrder: InvitationItemOrder,
    onOrderChange: (InvitationItemOrder) -> Unit
){

    val timeSelected = invitationItemOrder::class == InvitationItemOrder.Time::class
    NavigationDrawerItem(
        label = {
            IconRow(
                text = ListStrings.TIME,
                isChecked = timeSelected
            )
        },
        selected = false,
        onClick = {
            onOrderChange(InvitationItemOrder.Time(invitationItemOrder.sortingDirection, invitationItemOrder.showHosted, invitationItemOrder.showApproved))
        }
    )

    Divider()

    val sortDownSelected = invitationItemOrder.sortingDirection == SortingDirection.Down
    NavigationDrawerItem(
        label = {
            IconRow(
                text = ListStrings.SORT_DOWN,
                isChecked = sortDownSelected
            )
        },
        selected = false,
        onClick = {
            onOrderChange(invitationItemOrder.copy(SortingDirection.Down, invitationItemOrder.showHosted, invitationItemOrder.showApproved))
        }
    )

    val sortUpSelected = invitationItemOrder.sortingDirection == SortingDirection.Up
    NavigationDrawerItem(
        label = {
            IconRow(
                text = ListStrings.SORT_UP,
                isChecked = sortUpSelected
            )
        },
        selected = false,
        onClick = {
            onOrderChange(invitationItemOrder.copy(SortingDirection.Up, invitationItemOrder.showHosted, invitationItemOrder.showApproved))
        }
    )

    Divider()

    NavigationDrawerItem(
        label = {
            IconRow(text = ListStrings.SHOW_CELEBRATED, isChecked = invitationItemOrder.showHosted)
        },
        selected = false,
        onClick = {
            onOrderChange(invitationItemOrder.copy(invitationItemOrder.sortingDirection, !invitationItemOrder.showHosted, invitationItemOrder.showApproved))
        }
    )

    Divider()

    NavigationDrawerItem(
        label = {
            IconRow(text = ListStrings.SHOW_APPROVED, isChecked = invitationItemOrder.showApproved)
        },
        selected = false,
        onClick = {
            onOrderChange(invitationItemOrder.copy(invitationItemOrder.sortingDirection, invitationItemOrder.showHosted, !invitationItemOrder.showApproved))
        }
    )
}