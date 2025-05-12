package com.example.trial_junior.feature_junior.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trial_junior.feature_junior.presentation.util.Screen
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape

val Rust = Color(0xFFB7410E)
data class BottomNavItem(
    val title: String,
    val selectedIcon: Any,
    val unselectedIcon: Any,
    val route: String
)

@Composable
fun AppBottomNavigation(navController: NavHostController) {
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    val bottomNavItems = remember {
        listOf(
            BottomNavItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = Screen.LandingScreen.route
            ),
            BottomNavItem(
                title = "Birthday",
                selectedIcon = Icons.Filled.CardGiftcard,
                unselectedIcon = Icons.Outlined.CardGiftcard,
                route = Screen.InvitationScreen.route
            ),
            BottomNavItem(
                title = "Interview",
                selectedIcon = Icons.Filled.Videocam,
                unselectedIcon = Icons.Outlined.Videocam,
                route = Screen.BasicInterviewScreen.route
            ),
            BottomNavItem(
                title = "Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                route = Screen.ProfileScreen.route
            )
        )
    }

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.LightGray, // Adjust the color to match your design
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp) // Optional: rounded corners if needed
            )
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    val currentRoute = navController.currentDestination?.route
                    if (currentRoute != item.route) {
                        selectedItemIndex = index
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selectedItemIndex == index) Rust else Color.Black
                    )
                },
                icon = {
                    val icon = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon
                    val tint = if (index == selectedItemIndex) Rust else Color.Black
                    when (icon) {
                        is ImageVector -> Icon(imageVector = icon, contentDescription = item.title, tint = tint)
                        is Painter -> Icon(painter = icon, contentDescription = item.title, tint = tint)
                        else -> Icon(Icons.Filled.Home, contentDescription = "Unknown Icon", tint = tint)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Rust,
                    selectedTextColor = Rust,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}