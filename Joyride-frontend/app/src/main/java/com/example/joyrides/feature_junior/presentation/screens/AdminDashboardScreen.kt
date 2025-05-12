package com.example.trial_junior.feature_junior.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trial_junior.feature_junior.presentation.components.AppBottomNavigation
import com.example.trial_junior.feature_junior.presentation.components.BarGraph
import com.example.trial_junior.feature_junior.presentation.components.HalfScreenMenu
import com.example.trial_junior.feature_junior.presentation.components.SharedDashboardComponents
import com.example.trial_junior.feature_junior.presentation.components.StatisticCards
import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterviewListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.WishListViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AdminDashboardScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    invitationViewModel: InvitationListViewModel = hiltViewModel(),
    wishListViewModel: WishListViewModel = hiltViewModel(),
    basicInterviewViewModel: BasicInterviewListViewModel = hiltViewModel(),
    specialInterviewViewModel: SpecialInterviewListViewModel = hiltViewModel()
) {
    val localNavController = rememberNavController()
    var currentScreen by remember { mutableStateOf("Interview Management") }
    var isMenuOpen by remember { mutableStateOf(false) }

    // Fetch data
    LaunchedEffect(Unit) {
        invitationViewModel.getItems()
        wishListViewModel.getItems()
        basicInterviewViewModel.getItems()
        specialInterviewViewModel.getItems()
    }

    val invitationState by invitationViewModel.state
    val wishlistState by wishListViewModel.state
    val basicInterviewState by basicInterviewViewModel.state
    val specialInterviewState by specialInterviewViewModel.state

    // Calculate grand total across all categories
    val grandTotal = invitationState.items.size + wishlistState.items.size + specialInterviewState.items.size + basicInterviewState.items.size
    // Count upcoming items for each category
    val invitationUpcoming = invitationState.items.count { it.upcoming }
    val wishlistUpcoming = wishlistState.items.count { it.upcoming }
    val specialInterviewUpcoming = specialInterviewState.items.count { it.upcoming }
    val basicInterviewUpcoming = basicInterviewState.items.count { it.upcoming }
    // Calculate percentages based on grand total
    val invitationPercent = if (grandTotal > 0) (invitationUpcoming * 100 / grandTotal) else 0
    val wishlistPercent = if (grandTotal > 0) (wishlistUpcoming * 100 / grandTotal) else 0
    val specialInterviewPercent = if (grandTotal > 0) (specialInterviewUpcoming * 100 / grandTotal) else 0
    val basicInterviewPercent = if (grandTotal > 0) (basicInterviewUpcoming * 100 / grandTotal) else 0

    val barGraphData = listOf(invitationPercent, wishlistPercent, specialInterviewPercent, basicInterviewPercent)

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content with bottom bar
        Scaffold(
            bottomBar = {
                if (!isMenuOpen) {
                    AppBottomNavigation(navController = navController)
                }
            },
            modifier = Modifier.zIndex(1f)
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Fixed top content
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 5.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Admin",
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .padding(0.dp)
                    )
                    SharedDashboardComponents(
                        onTimeRangeSelected = { range ->
                            // Handle time range selection if needed
                        }
                    )
                    ManagementSection { selectedSection ->
                        currentScreen = selectedSection
                        when (selectedSection) {
                            "Interview Management" -> localNavController.navigate("interviewManagement") {
                                popUpTo(localNavController.graph.startDestinationId) { inclusive = true }
                            }
                            "Birthday Management" -> localNavController.navigate("birthdayManagement") {
                                popUpTo(localNavController.graph.startDestinationId) { inclusive = true }
                            }
                        }
                    }

                    // Scrollable content starting from StatisticCards
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp), // Adjust top padding to align with previous spacing
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            StatisticCards(
                                usersCount = 1234,
                                eventsCount = 156,
                                showsCount = 89,
                                birthdaysCount = 45,
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        item {
                            Text(
                                text = "Weekly Activities Report",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 16.dp, start = 26.dp)
                            )
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp).padding(start = 6.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                BarGraph(
                                    data = barGraphData
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                        item {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                NavHost(navController = localNavController, startDestination = "interviewManagement") {
                                    composable("interviewManagement") {
                                        InterviewManagementScreen(
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                    composable("birthdayManagement") {
                                        BirthdayManagementScreen(
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                // Toggle menu
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = { isMenuOpen = !isMenuOpen }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open Menu",
                            tint = Color.Black
                        )
                    }
                }
            }
        }

        // Side Menu (higher z-index to overlay everything)
        if (isMenuOpen) {
            HalfScreenMenu(
                navController = navController,
                isMenuOpen = remember { mutableStateOf(isMenuOpen) },
                modifier = Modifier.zIndex(2f),
                onDismiss = { isMenuOpen = false }
            )
        }
    }
}

@Composable
fun ManagementSection(onSectionSelected: (String) -> Unit = {}) {
    var selected by remember { mutableStateOf("") } // Track which item is selected

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Birthday Management
        ManagementItem(
            title = "Birthday Management",
            isSelected = selected == "Birthday Management",
            onSectionSelected = { newSelection ->
                selected = newSelection
                onSectionSelected(newSelection)
            }
        )

        // Birthday Management
        ManagementItem(
            title = "Interview Management",
            isSelected = selected == "Interview Management",
            onSectionSelected = { newSelection ->
                selected = newSelection
                onSectionSelected(newSelection)
            }
        )
    }
}

@Composable
fun ManagementItem(title: String, isSelected: Boolean, onSectionSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onSectionSelected(title) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        title.split(" ").forEach { word ->
            Text(
                text = word,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color(0xFF615517) else MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(
            modifier = Modifier
                .width(170.dp)
                .height(8.dp)
                .background(if (isSelected) Color(0xFF615517) else Color(0xFFD9D9D9))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AdminDashboardScreenPreview() {
    AdminDashboardScreen(
        navController = rememberNavController(),
        invitationViewModel = hiltViewModel(),
        wishListViewModel = hiltViewModel(),
        basicInterviewViewModel = hiltViewModel(),
        specialInterviewViewModel = hiltViewModel()
    )
}


