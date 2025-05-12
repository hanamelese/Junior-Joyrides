package com.example.trial_junior.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.trial_junior.feature_junior.domain.model.InvitationItem


data class ItemColors(
    val backgroundColor: Color,
    val textColor: Color,
    val editIconColor: Color,
    val checkColor: Color
)

@Composable
fun getItemColors(invitation: InvitationItem): ItemColors {
    return ItemColors(
            backgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
            textColor = MaterialTheme.colorScheme.onBackground,
            editIconColor = MaterialTheme.colorScheme.onBackground,
            checkColor = if(invitation.upcoming) MaterialTheme.colorScheme.onBackground
            else MaterialTheme.colorScheme.tertiary
        )

}