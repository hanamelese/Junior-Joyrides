package com.example.trial_junior.feature_junior.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.trial_junior.R
import com.example.trial_junior.feature_junior.presentation.util.Screen

@Composable
fun MenuHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Full Name", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector,
)

@Composable
fun MenuBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit
) {
    Column(modifier) {
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Contact us",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_youtube),
                    contentDescription = "YouTube"
                )
            }
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = "Instagram"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_call),
                    contentDescription = "Phone",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun HalfScreenMenu(
    navController: NavHostController,
    isMenuOpen: androidx.compose.runtime.MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .widthIn(max = 300.dp)
            .background(Color.White)
    ) {
        MenuHeader()
        MenuBody(
            items = listOf(
                MenuItem(
                    id = "home",
                    title = "Home",
                    contentDescription = "Go to home screen",
                    icon = Icons.Default.Home
                ),
                MenuItem(
                    id = "birthday",
                    title = "Birthday",
                    contentDescription = "View birthdays",
                    icon = Icons.Default.CardGiftcard
                ),
                MenuItem(
                    id = "interview",
                    title = "Interview",
                    contentDescription = "Schedule interview",
                    icon = Icons.Default.Videocam
                ),
                MenuItem(
                    id = "profile",
                    title = "Profile",
                    contentDescription = "View profile",
                    icon = Icons.Default.Person

                ),
            ),
            onItemClick = { menuItem ->
                Log.d("Menu Item Clicked", menuItem.title)
                isMenuOpen.value = false
                onDismiss()
                when (menuItem.id) {
                    "home" -> {
                        navController.navigate(Screen.LandingScreen.route)
                    }

                    "birthday" -> {
                        navController.navigate(Screen.InvitationScreen.route)
                    }

                    "interview" -> {
                        navController.navigate(Screen.BasicInterviewScreen.route)
                    }

                    "profile" -> {
                        navController.navigate(Screen.ProfileScreen.route)
                    }
                }
            }
        )
    }
}