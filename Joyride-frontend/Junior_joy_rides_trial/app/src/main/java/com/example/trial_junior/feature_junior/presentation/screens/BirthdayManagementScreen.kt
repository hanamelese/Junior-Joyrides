package com.example.trial_junior.feature_junior.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.trial_junior.feature_junior.presentation.components.WeekNavigator
import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.WishListViewModel

@Composable
fun BirthdayManagementScreen(
    modifier: Modifier = Modifier,
    viewModel: InvitationListViewModel = hiltViewModel(),
    wishViewModel: WishListViewModel = hiltViewModel()
) {
    val invitationItems by viewModel.state
    val wishListItems by wishViewModel.state

    LaunchedEffect(Unit) {
        viewModel.getItems()
        wishViewModel.getItems()
    }

    Column(modifier = modifier.padding(16.dp)) {
        // First Card: Recent Invitations
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Recent Invitations", style = MaterialTheme.typography.titleLarge)

                if (invitationItems.items.isEmpty()) {
                    Text(
                        "No recent invitations available",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .heightIn(max = 300.dp) // set max height to allow scrolling
                            .verticalScroll(rememberScrollState())
                    ) {
                        invitationItems.items.forEach { invitation ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.Circle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                    Text(
                                        "New Birthday Invitation",
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                                Text(
                                    "${invitation.childName} booked a birthday celebration",
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Text("Date: ${invitation.date}", fontWeight = FontWeight.Bold)
                                Text("Guardian Contact: ${invitation.guardianEmail}")
                                Text(
                                    "... Show More",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = 4.dp)
                                )

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


        // Second Card: Next Week Wishlist
//        Card(
//            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text("Next Week Wishlist", style = MaterialTheme.typography.titleLarge)
//
//                if (wishListItems.items.isEmpty()) {
//                    Text("No upcoming birthdays 🎈", modifier = Modifier.padding(top = 8.dp))
//                } else {
//                    val context = LocalContext.current
//
//                    wishListItems.items.forEach { item ->
//                        Card(
//                            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
//                            shape = RoundedCornerShape(12.dp)
//                        ) {
//                            Column(modifier = Modifier.padding(8.dp)) {
//                                Text("${item.childName} 🎉", fontWeight = FontWeight.Bold)
//                                Text("Date: ${item.date}")
//                                Text("Age: ${item.age}")
//                                Text("Guardian: ${item.guardianEmail}")
//                                Text("Special Requests: ${item.specialRequests}")
//
//                                if (item.imageUrl.isNotBlank()) {
//                                    val request = ImageRequest.Builder(context)
//                                        .data(item.imageUrl)
//                                        .crossfade(true)
//                                        .listener(
//                                            onError = { _, result ->
//                                                Log.e(
//                                                    "AsyncImageError",
//                                                    "Failed to load image: ${item.imageUrl}",
//                                                    result.throwable
//                                                )
//                                            }
//                                        )
//                                        .build()
//
//                                    AsyncImage(
//                                        model = request,
//                                        contentDescription = "${item.childName}'s wish image",
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .height(100.dp)
//                                            .padding(top = 8.dp),
//                                        contentScale = ContentScale.Crop
//                                    )
//                                } else {
//                                    Log.w("AsyncImageWarning", "Image URL is blank for ${item.childName}")
//                                }
//                            }
//                        }
//                    }
        //      }
        //     }
        //}
        WeekNavigator(viewModel = wishViewModel)
    }
}

// Mock Data Class (for preview or test)
data class WishListItem(
    val id: Int = 0,
    val childName: String = "Unknown",
    val date: String = "2024-05-10",
    val guardianEmail: String = "guardian@example.com",
    val age: Int = 0,
    val wishList: String = "",
    val imageUrl: String = ""
)

@Preview
@Composable
fun BirthdayManagementScreenPreview() {
    BirthdayManagementScreen()
}




//package com.example.trial_junior.feature_junior.presentation.screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Circle
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListViewModel
//import com.example.trial_junior.feature_junior.presentation.viewModels.WishListViewModel
//
//@Composable
//fun BirthdayManagementScreen(
//    modifier: Modifier = Modifier,
//    wishListItems: List<WishListItem> = emptyList(),
//    viewModel: InvitationListViewModel = hiltViewModel(),
//    wishViewModel: WishListViewModel = hiltViewModel()
//) {
//    val invitationItems by viewModel.state // Ensures UI recomposes when state updates
//
//    LaunchedEffect(Unit) {
//        viewModel.getItems() // Ensure data fetch on screen load
//    }
//
//    val wishListItems by wishViewModel.state // Ensure proper reactivity
//
//   LaunchedEffect(Unit) {
//        wishViewModel.getItems() // Fetch items when screen loads
//    }
//
//    Column(modifier = modifier.padding(16.dp)) {
//
//        // Recent Invitations (Dynamically Fetch from ViewModel)
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text("Recent Invitations", style = MaterialTheme.typography.titleLarge)
//
//                if (invitationItems.items.isEmpty()) {
//                    Text("No recent invitations available", modifier = Modifier.padding(top = 8.dp))
//                } else {
//                    invitationItems.items.forEach { invitation ->
//                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                Icon(Icons.Default.Circle, contentDescription = null, tint = MaterialTheme.colorScheme.error)
//                                Text("New Birthday Invitation", modifier = Modifier.padding(start = 8.dp))
//                            }
//                            Text("${invitation.childName} booked a birthday celebration", modifier = Modifier.padding(top = 4.dp))
//                            Text("Date: ${invitation.date}", fontWeight = FontWeight.Bold)
//                            Text("Guardian Contact: ${invitation.guardianEmail}")
//                            Text("... Show More", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 4.dp))
//
//                            Divider(
//                                modifier = Modifier.padding(vertical = 8.dp),
//                                thickness = 1.dp,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//                    }
//                }
//            }
//        }
//
//
//            // Next Week Wishlist (Fetching Data Dynamically)
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp),
//                shape = RoundedCornerShape(12.dp)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text("Next Week Wishlist", style = MaterialTheme.typography.titleLarge)
//
//                    if (wishListItems.items.isEmpty()) {
//                        Text("No upcoming birthdays 🎈", modifier = Modifier.padding(top = 8.dp))
//                    } else {
//                        wishListItems.items.forEach { item ->
//                            Card(
//                                modifier = Modifier
//                                    .padding(vertical = 8.dp)
//                                    .fillMaxWidth(),
//                                shape = RoundedCornerShape(12.dp)
//                            ) {
//                                Column(modifier = Modifier.padding(8.dp)) {
//                                    Text("${item.childName} 🎉", fontWeight = FontWeight.Bold)
//                                    Text("Date: ${item.date}")
//                                    Text("Age: ${item.age}")
//                                    Text("Guardian: ${item.guardianEmail}")
//                                    Text("Special Requests: ${item.specialRequests}")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//// Mock Data Class
//data class WishListItem(
//    val id: Int = 0,
//    val childName: String = "Unknown",
//    val date: String = "2024-05-10", // yyyy-MM-dd format
//    val guardianEmail: String = "guardian@example.com",
//    val age: Int = 0,
//    val wishList: String = ""
//)
//
//@Preview
//@Composable
//fun BirthdayManagementScreenPreview() {
//    BirthdayManagementScreen(
//        wishListItems = listOf(
//            WishListItem(1, "Alice", "2024-05-09", "alice_guardian@example.com", 7),
//            WishListItem(2, "Bob", "2024-05-12", "bob_guardian@example.com", 9)
//        )
//    )
//}










//
//@Composable
//fun BirthdayManagementScreen(
//    modifier: Modifier = Modifier,
//    viewModel: WishListViewModel = hiltViewModel()
//) {
//    val wishListItems by viewModel.state // Ensure proper reactivity
//
//    LaunchedEffect(Unit) {
//        viewModel.getItems() // Fetch items when screen loads
//    }
//
//
//        }
//    }
//}