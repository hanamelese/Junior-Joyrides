package com.example.trial_junior.feature_junior.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trial_junior.feature_junior.domain.model.BasicInterviewItem
import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem
import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterviewListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewListViewModel
import kotlinx.coroutines.launch

@Composable
fun InterviewManagementScreen(
    modifier: Modifier = Modifier,
    basicViewModel: BasicInterviewListViewModel = hiltViewModel(),
    specialViewModel: SpecialInterviewListViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val basicState by basicViewModel.state
    val specialState by specialViewModel.state
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        basicViewModel.getItems()
        specialViewModel.getItems()
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            // --- Upcoming Interviews ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
                    Text("Upcoming Interviews", style = MaterialTheme.typography.titleLarge)

                    when {
                        basicState.isLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 16.dp)
                            )
                        }

                        basicState.error != null -> {
                            Text(
                                text = basicState.error ?: "An error occurred",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }

                        else -> {
                            val itemsToDisplay = basicState.items.filter { it.upcoming && !it.approved }

                            if (itemsToDisplay.isEmpty()) {
                                Text("No upcoming interviews available", modifier = Modifier.padding(top = 8.dp))
                            } else {
                                itemsToDisplay.forEach { interview ->
                                    BasicInterviewItemRow(interview)
                                    Divider(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- Videos for Approval ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp)
                    .shadow(4.dp, shape = RectangleShape)
            ) {
                Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
                    Text("Videos for Approval", style = MaterialTheme.typography.titleLarge)

                    // Debug: Show total and filtered item counts
                    Text(
                        text = "Total Special Items: ${specialState.items.size}",
                        modifier = Modifier.padding(top = 8.dp),
                        color = Color.Gray
                    )

                    val unapprovedItems = specialState.items.filter { !it.approved }
                    Text(
                        text = "Unapproved Items: ${unapprovedItems.size}",
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                        color = Color.Gray
                    )

                    if (unapprovedItems.isEmpty()) {
                        Text(
                            text = "No videos for approval",
                            modifier = Modifier.padding(top = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Column {
                            // Split into pairs for two-column layout
                            unapprovedItems.chunked(2).forEach { pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    pair.forEachIndexed { index, item ->
                                        VideoCard(
                                            name = item.childName,
                                            age = item.age,
                                            guardianName = item.guardianName,
                                            isApprovedSection = false,
                                            onApproveClick = {
                                                if (!item.approved) {
                                                    specialViewModel.onEvent(SpecialInterviewEvent.ToggleApproved(item))
                                                }
                                            },
                                            onCancelClick = {
                                                specialViewModel.onEvent(SpecialInterviewEvent.Delete(item))
                                                scope.launch {
                                                    val result = snackbarHostState.showSnackbar(
                                                        message = "Video Cancelled",
                                                        actionLabel = "Undo",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                    if (result == SnackbarResult.ActionPerformed) {
                                                        specialViewModel.onEvent(SpecialInterviewEvent.UndoDelete(item))
                                                    }
                                                }
                                            },
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(
                                                    start = if (index == 1) 8.dp else 0.dp,
                                                    end = if (index == 0) 8.dp else 0.dp,
                                                    top = 16.dp,
                                                    bottom = 8.dp
                                                )
                                        )
                                    }
                                    // Add empty spacer if the pair has only one item
                                    if (pair.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }

                            Button(
                                onClick = { /* TODO: Show More logic */ },
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Show More")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- Approved Videos ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp)
                    .shadow(4.dp, shape = RectangleShape)
            ) {
                Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
                    Text("Videos that have been Approved", style = MaterialTheme.typography.titleLarge)

                    // Debug: Show filtered item count
                    val approvedItems = specialState.items.filter { it.approved }
                    Text(
                        text = "Approved Items: ${approvedItems.size}",
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                        color = Color.Gray
                    )

                    if (approvedItems.isEmpty()) {
                        Text(
                            text = "No approved videos",
                            modifier = Modifier.padding(top = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Column {
                            approvedItems.chunked(2).forEach { pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    pair.forEachIndexed { index, item ->
                                        VideoCard(
                                            name = item.childName,
                                            age = item.age,
                                            contact = item.guardianEmail,
                                            isApprovedSection = true,
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(
                                                    start = if (index == 1) 8.dp else 0.dp,
                                                    end = if (index == 0) 8.dp else 0.dp,
                                                    top = 8.dp,
                                                    bottom = 8.dp
                                                )
                                        )
                                    }
                                    if (pair.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }

                            Button(
                                onClick = { /* TODO: Show More logic */ },
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Show More")
                            }
                        }
                    }
                }
            }
        }

        // Custom Snackbar placement
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
fun BasicInterviewItemRow(interview: BasicInterviewItem) {
    val errorColor = MaterialTheme.colorScheme.error
    val statusText = if (interview.upcoming) "Status: Upcoming" else "Status: Completed"
    val approvedText = if (interview.approved) "Approved Interview" else "New Interview Booking"
    val approvedColor = if (interview.approved) Color.Green else errorColor

    Column(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Circle, contentDescription = null, tint = approvedColor)
            Text(
                text = approvedText,
                modifier = Modifier.padding(start = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = "${interview.childName} (Age: ${interview.age})",
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = "Guardian: ${interview.guardianName}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = "Contact: ${interview.guardianPhone}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = "Email: ${interview.guardianEmail}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        if (interview.specialRequests.isNotEmpty()) {
            Text(
                text = "Special Requests: ${interview.specialRequests}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(text = statusText, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    name: String,
    age: Int,
    guardianName: String? = null, // For unapproved items
    contact: String? = null, // For approved items
    isApprovedSection: Boolean,
    onApproveClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.shadow(4.dp, shape = RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(Icons.Default.Videocam, contentDescription = null)
            Text("Name: $name", modifier = Modifier.padding(top = 4.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("Age: $age", modifier = Modifier.padding(top = 4.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)

            if (isApprovedSection) {
                contact?.let {
                    Text("Contact: $it", modifier = Modifier.padding(top = 4.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            } else {
                guardianName?.let {
                    Text("Guardian: $it", modifier = Modifier.padding(top = 4.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Button(
                        onClick = onApproveClick,
                        modifier = Modifier.height(20.dp).width(70.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Approve", color = Color.White, style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onCancelClick,
                        modifier = Modifier.height(20.dp).width(75.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                    ) {
                        Text("Cancel", color = Color.White, style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InterviewManagementScreenPreview() {
    Surface {
        InterviewManagementScreen()
    }
}
