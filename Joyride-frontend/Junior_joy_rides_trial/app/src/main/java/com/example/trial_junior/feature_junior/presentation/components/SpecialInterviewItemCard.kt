package com.example.trial_junior.feature_junior.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.*
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
import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem
import com.example.trial_junior.ui.theme.TrialJuniorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialInterviewItemCard(
    specialInterview: SpecialInterviewItem,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F8F8)),
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
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 6.dp, bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Event,
                        contentDescription = "Event Icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 12.dp)
                    )
                    Column {
                        Text(
                            text = "Special Interview",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                AssistChip(
                    onClick = { /* No action for status chip */ },
                    label = {
                        Text(
                            text = if (specialInterview.upcoming) "PENDING" else "SCHEDULED",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (specialInterview.upcoming) Color(0xFFE4851C) else Color(0xFF4CAF50),
                        labelColor = Color.White
                    ),
                    modifier = Modifier.padding(start = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color.White)
                )
            }
            Text(
                text = "Child Name: ${specialInterview.childName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "Guardian Name: ${specialInterview.guardianName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "Age: ${specialInterview.age}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Divider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 14.dp, bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable(onClick = onEditClick)
                        .padding(4.dp)
                ) {
                    EditButton(
                        onEditClick = onEditClick,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Edit",
                        color = Color.Black,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable(onClick = onDeleteClick)
                        .padding(4.dp)
                ) {
                    DeleteButton(
                        onDeleteClick = onDeleteClick,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Cancel",
                        color = Color.Red,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SpecialInterviewItemCardPreview() {
    TrialJuniorTheme {
        SpecialInterviewItemCard(
            specialInterview = SpecialInterviewItem(
                childName = "Meba",
                guardianName = "Freail",
                guardianEmail = "rita@gmail.com",
                upcoming = true,
                approved = true,
                specialRequests = "Birthday celebration with the ethipis",
                guardianPhone = 988984673,
                age = 3,
                videoUrl = "https://www.youtube.com/watch?v=0wjnk62I01c&list=PPSV",
                id = 1
            ),
            onDeleteClick = {},
            onEditClick = {},
        )
    }
}
