package com.example.trial_junior.feature_junior.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trial_junior.core.presentation.components.DeleteButton
import com.example.trial_junior.core.presentation.components.EditButton
import com.example.trial_junior.core.presentation.components.HostedButton
import com.example.trial_junior.feature_junior.domain.model.InvitationItem
import com.example.trial_junior.ui.theme.TrialJuniorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvitationItemCardAdmin(
    invitation: InvitationItem,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onToggleHostedClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // Color(0xFFFEF9EB)
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top // Changed to align the top of the icon and title
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 6.dp, bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CardGiftcard,
                        contentDescription = "Gift Icon",
                        tint = MaterialTheme.colorScheme.onBackground, // Color(0xFF1D1C14)
                        modifier = Modifier
                            .size(28.dp)
                            .padding(end = 12.dp)
                    )
                    Column {
                        Text(
                            text = "Birthday",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground, // Color(0xFF1D1C14)
                            fontSize = 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Celebration",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground, // Color(0xFF1D1C14)
                            fontSize = 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 0.dp)
                        )
                    }
                }
                AssistChip(
                    onClick = { /* No action for status chip */ },
                    label = {
                        Text(
                            text = if (invitation.upcoming) "PENDING" else "SCHEDULED",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (invitation.upcoming) Color(0xFFE4851C) else Color(0xFF4CAF50), // Orange for PENDING, Green for SCHEDULED
                        labelColor = Color.White
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = "Date: ${invitation.date}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground, // Color(0xFF1D1C14)
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "Birthday celebration with ${invitation.childName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground, // Color(0xFF1D1C14)
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
            Divider(
                color = MaterialTheme.colorScheme.outline, // Color(0xFF7B7768)
                thickness = 1.dp,
                modifier = Modifier.padding(top = 14.dp, bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp),
                horizontalArrangement = Arrangement.End
            ) {
                HostedButton(
                    onToggleHostedClick = onToggleHostedClick,
                    color = MaterialTheme.colorScheme.secondary,
                    upcoming = invitation.upcoming,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                EditButton(
                    onEditClick = onEditClick,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                DeleteButton(
                    onDeleteClick = onDeleteClick,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}




@Preview
@Composable
fun InvitationItemCardAdminPreview() {
    TrialJuniorTheme {
        InvitationItemCardAdmin(
            invitation = InvitationItem(
                childName = "Meba",
                guardianEmail = "rita@gmail.com",
                time = 112345,
                upcoming = true, // Set to true to show "PENDING"
                approved = true,
                specialRequests = "Birthday celebration with the ethipis",
                address = "America",
                date = "2025-03-27",
                guardianPhone = 988984673,
                age = 3,
                id = 1,
                userId = 2
            ),
            onDeleteClick = {},
            onEditClick = {},
            onToggleHostedClick = {}
        )
    }
}








