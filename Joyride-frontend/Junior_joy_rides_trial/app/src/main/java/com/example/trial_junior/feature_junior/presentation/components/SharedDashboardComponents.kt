package com.example.trial_junior.feature_junior.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SharedDashboardComponents(
    modifier: Modifier = Modifier,
    onTimeRangeSelected: (String) -> Unit
) {
    Column(modifier = modifier) {
        // "Admin Dashboard" text
       Box(
            modifier = Modifier
                .fillMaxWidth()
               .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Admin Dashboard",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Time range selector
        var selected by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 30.dp)
                .background(color = Color(0xFFD9D9D9), shape = RoundedCornerShape(20.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Week", "Month", "Year").forEach { timeframe ->
                    Text(
                        text = timeframe,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selected == timeframe) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .background(
                                color = if (selected == timeframe) Color(0xFFC5AE3D) else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                            .clickable {
                                selected = timeframe
                                onTimeRangeSelected(timeframe)
                            }
                    )
                }
            }
        }
    }
    // Stats Section with StatisticCards

    // Weekly Activities Report

}



@Composable
fun StatisticCards(usersCount: Int, eventsCount: Int, showsCount: Int, birthdaysCount: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("Users", usersCount.toString(), Icons.Default.People, Color.Red)
            StatCard("Events", eventsCount.toString(), Icons.Default.Event, Color(0xFF40E0D0))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("Shows", showsCount.toString(), Icons.Default.Videocam, Color.Yellow)
            StatCard("Birthdays", birthdaysCount.toString(), Icons.Default.Celebration, Color.Green)
        }
    }
}

@Composable
fun StatCard(label: String, count: String, icon: ImageVector, iconTint: Color) {
    Card(
        modifier = Modifier
            .size(140.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Set to white
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = count, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = label, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}