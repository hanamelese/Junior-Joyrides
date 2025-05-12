package com.example.trial_junior.feature_junior.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.trial_junior.R
import com.example.trial_junior.core.util.ContentDescriptions
import com.example.trial_junior.feature_junior.domain.model.BasicInterviewItem
import com.example.trial_junior.feature_junior.domain.model.InvitationItem
import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem
import com.example.trial_junior.feature_junior.domain.model.WishListItem
import com.example.trial_junior.feature_junior.presentation.components.AppBottomNavigation
import com.example.trial_junior.feature_junior.presentation.components.BasicInterviewItemCard
import com.example.trial_junior.feature_junior.presentation.components.InvitationItemCard
import com.example.trial_junior.feature_junior.presentation.components.HalfScreenMenu
import com.example.trial_junior.feature_junior.presentation.components.SpecialInterviewItemCard
import com.example.trial_junior.feature_junior.presentation.components.WishlistItemCard
import com.example.trial_junior.feature_junior.presentation.util.Screen
import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterviewEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterviewListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.UserUiEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.UserViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.WishListEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.WishListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel(),
    invitationViewModel: InvitationListViewModel = hiltViewModel(),
    basicInterviewViewModel: BasicInterviewListViewModel = hiltViewModel(),
    specialInterviewViewModel: SpecialInterviewListViewModel = hiltViewModel(),
    wishListViewModel: WishListViewModel = hiltViewModel()
) {
    val userState by userViewModel.state.collectAsState()
    val user = userState.user
    val snackbarHostState = remember { SnackbarHostState() }
    var isMenuOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Fetch user profile data and items on initial load and after navigation
    LaunchedEffect(Unit) {
        userViewModel.getMyProfile()
    }

    // Listen for snackbar events from UserViewModel
    LaunchedEffect(Unit) {
        userViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UserUiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = event.message)
                    }
                }
                else -> {}
            }
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(bottom = 16.dp), // Added bottom padding
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .background(Color.Gray), // Always set the gray background
                        contentAlignment = Alignment.Center
                    ) {
                        // Overlay background image if available and non-empty
                        if (user?.backgroundImageUrl?.isNotEmpty() == true) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = user.backgroundImageUrl,
                                ),
                                contentDescription = "Background Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(170.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        // Always show profile image (default or selected)
                        Image(
                            painter = if (user?.profileImageUrl?.isNotEmpty() == true) {
                                rememberAsyncImagePainter(
                                    model = user.profileImageUrl,
                                    placeholder = painterResource(id = R.drawable.profile),
                                    error = painterResource(id = R.drawable.profile)
                                )
                            } else {
                                painterResource(id = R.drawable.profile)
                            },
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(120.dp)
                                .absoluteOffset(y = 95.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = user?.let { "${it.firstName} ${it.lastName}" } ?: "Full Name",
                        fontSize = 26.sp,
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                    )
                    Text(
                        text = user?.email ?: "name@gmail.com",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            navController.navigate(Screen.EditProfileScreen.route) {
                                popUpTo(Screen.ProfileScreen.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier
                            .width(201.dp)
                            .height(44.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDEDEDE)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = ContentDescriptions.UPDATE_ITEM,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("Edit profile", fontSize = 20.sp, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(45.dp))
                    Text(
                        text = "My Applications",
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }

                items((user?.invitations ?: emptyList()) + (user?.basicInterviews ?: emptyList()) +
                        (user?.specialInterviews ?: emptyList()) + (user?.wishLists ?: emptyList())) { item ->
                    when (item) {
                        is InvitationItem -> {
                            InvitationItemCard(
                                invitation = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                onDeleteClick = {
                                    invitationViewModel.onEvent(InvitationListEvent.Delete(item))
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = "Invitation Deleted",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (undo == SnackbarResult.ActionPerformed) {
                                            invitationViewModel.onEvent(InvitationListEvent.UndoDelete(item))
                                        }
                                    }
                                },
                                onEditClick = {
                                    navController.navigate(
                                        Screen.InvitationScreen.route + "?invitationId=${item.id}"
                                    )
                                }
                            )
                        }
                        is BasicInterviewItem -> {
                            BasicInterviewItemCard(
                                basicInterview = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                onDeleteClick = {
                                    basicInterviewViewModel.onEvent(BasicInterviewEvent.Delete(item))
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = "Basic Interview Deleted",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (undo == SnackbarResult.ActionPerformed) {
                                            basicInterviewViewModel.onEvent(BasicInterviewEvent.UndoDelete(item))
                                        }
                                    }
                                },
                                onEditClick = {
                                    navController.navigate(
                                        Screen.BasicInterviewScreen.route + "?basicInterviewId=${item.id}"
                                    )
                                }
                            )
                        }
                        is SpecialInterviewItem -> {
                            SpecialInterviewItemCard(
                                specialInterview = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                onDeleteClick = {
                                    specialInterviewViewModel.onEvent(SpecialInterviewEvent.Delete(item))
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = "Special Interview Deleted",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (undo == SnackbarResult.ActionPerformed) {
                                            specialInterviewViewModel.onEvent(SpecialInterviewEvent.UndoDelete(item))
                                        }
                                    }
                                },
                                onEditClick = {
                                    navController.navigate(
                                        Screen.SpecialInterviewScreen.route + "?specialInterviewId=${item.id}"
                                    )
                                }
                            )
                        }
                        is WishListItem -> {
                            WishlistItemCard(
                                wishlistItem = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                onDeleteClick = {
                                    wishListViewModel.onEvent(WishListEvent.Delete(item))
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = "Wishlist Item Deleted",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (undo == SnackbarResult.ActionPerformed) {
                                            wishListViewModel.onEvent(WishListEvent.UndoDelete(item))
                                        }
                                    }
                                },
                                onEditClick = {
                                    navController.navigate(
                                        Screen.WishListScreen.route + "?wishListId=${item.id}"
                                    )
                                }
                            )
                        }
                    }
                }
            }

            // Loading Indicator
            if (userState.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }

            // Snackbar for error messages
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            // Side Menu
            if (isMenuOpen) {
                HalfScreenMenu(
                    navController = navController,
                    isMenuOpen = remember { mutableStateOf(isMenuOpen) },
                    modifier = Modifier,
                    onDismiss = { isMenuOpen = false }
                )
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




// package com.example.trial_junior.feature_junior.presentation.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.absoluteOffset
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.SnackbarDuration
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.SnackbarResult
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import com.example.trial_junior.R
//import com.example.trial_junior.core.util.ContentDescriptions
//import com.example.trial_junior.feature_junior.domain.model.BasicInterviewItem
//import com.example.trial_junior.feature_junior.domain.model.InvitationItem
//import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem
//import com.example.trial_junior.feature_junior.domain.model.WishListItem
//import com.example.trial_junior.feature_junior.presentation.components.BasicInterviewItemCard
//import com.example.trial_junior.feature_junior.presentation.components.InvitationItemCard
//import com.example.trial_junior.feature_junior.presentation.components.HalfScreenMenu
//import com.example.trial_junior.feature_junior.presentation.components.SpecialInterviewItemCard
//import com.example.trial_junior.feature_junior.presentation.components.WishlistItemCard
//import com.example.trial_junior.feature_junior.presentation.util.Screen
//import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterviewEvent
//import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterviewListViewModel
//import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListEvent
//import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListViewModel
//import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewEvent
//import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewListViewModel
//import com.example.trial_junior.feature_junior.presentation.viewModels.UserUiEvent
//import com.example.trial_junior.feature_junior.presentation.viewModels.UserViewModel
//import com.example.trial_junior.feature_junior.presentation.viewModels.WishListEvent
//import com.example.trial_junior.feature_junior.presentation.viewModels.WishListViewModel
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//@Composable
//fun ProfileScreen(
//    navController: NavHostController,
//    userViewModel: UserViewModel = hiltViewModel(),
//    invitationViewModel: InvitationListViewModel = hiltViewModel(),
//    basicInterviewViewModel: BasicInterviewListViewModel = hiltViewModel(),
//    specialInterviewViewModel: SpecialInterviewListViewModel = hiltViewModel(),
//    wishListViewModel: WishListViewModel = hiltViewModel()
//) {
//    val userState by userViewModel.state.collectAsState()
//    val user = userState.user
//    val snackbarHostState = remember { SnackbarHostState() }
//    var isMenuOpen by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()
//
//    // Fetch user profile data
//    LaunchedEffect(Unit) {
//        if (user == null) {
//            userViewModel.getMyProfile()
//        }
//    }
//
//    // Listen for snackbar events from UserViewModel
//    LaunchedEffect(Unit) {
//        userViewModel.eventFlow.collectLatest { event ->
//            when (event) {
//                is UserUiEvent.ShowSnackbar -> {
//                    scope.launch {
//                        snackbarHostState.showSnackbar(message = event.message)
//                    }
//                }
//                else -> {}
//            }
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(
//            modifier = Modifier
//                .background(Color.White)
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top
//        ) {
//            item {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(170.dp)
//                        .background(Color.Gray)
//                        .padding(bottom = 12.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.profile),
//                        contentDescription = "Profile Image",
//                        modifier = Modifier
//                            .size(120.dp)
//                            .absoluteOffset(y = 95.dp)
//                            .clip(CircleShape)
//                    )
//                }
//                Spacer(modifier = Modifier.height(60.dp))
//                Text(
//                    text = user?.let { "${it.firstName} ${it.lastName}" } ?: "Full Name",
//                    fontSize = 26.sp,
//                    color = Color.Black,
//                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
//                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
//                )
//                Text(
//                    text = user?.email ?: "name@gmail.com",
//                    fontSize = 20.sp,
//                    style = MaterialTheme.typography.headlineMedium,
//                    modifier = Modifier.padding(start = 16.dp)
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Button(
//                    onClick = {
//                        navController.navigate(Screen.EditProfileScreen.route) {
//                            popUpTo(Screen.ProfileScreen.route) { inclusive = false }
//                            launchSingleTop = true
//                        }
//                    },
//                    modifier = Modifier
//                        .width(201.dp)
//                        .height(44.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFFDEDEDE)
//                    )
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Edit,
//                        contentDescription = ContentDescriptions.UPDATE_ITEM,
//                        tint = Color.Black,
//                        modifier = Modifier
//                            .size(48.dp)
//                    )
//                    Spacer(modifier = Modifier.width(2.dp))
//                    Text("Edit profile", fontSize = 20.sp, color = Color.Black)
//                }
//                Spacer(modifier = Modifier.height(45.dp))
//                Text(
//                    text = "My Applications",
//                    fontSize = 30.sp,
//                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
//                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
//                )
//                Spacer(modifier = Modifier.height(25.dp))
//            }
//
//            items((user?.invitations ?: emptyList()) + (user?.basicInterviews ?: emptyList()) +
//                    (user?.specialInterviews ?: emptyList()) + (user?.wishLists ?: emptyList())) { item ->
//                when (item) {
//                    is InvitationItem -> {
//                        InvitationItemCard(
//                            invitation = item,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 8.dp),
//                            onDeleteClick = {
//                                invitationViewModel.onEvent(InvitationListEvent.Delete(item))
//                                scope.launch {
//                                    val undo = snackbarHostState.showSnackbar(
//                                        message = "Invitation Deleted",
//                                        actionLabel = "Undo",
//                                        duration = SnackbarDuration.Short
//                                    )
//                                    if (undo == SnackbarResult.ActionPerformed) {
//                                        invitationViewModel.onEvent(InvitationListEvent.UndoDelete(item))
//                                    }
//                                }
//                            },
//                            onEditClick = {
//                                navController.navigate("invitation_edit/${item.id}")
//                            }
//                        )
//                    }
//                    is BasicInterviewItem -> {
//                        BasicInterviewItemCard(
//                            basicInterview = item,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 8.dp),
//                            onDeleteClick = {
//                                basicInterviewViewModel.onEvent(BasicInterviewEvent.Delete(item))
//                                scope.launch {
//                                    val undo = snackbarHostState.showSnackbar(
//                                        message = "Basic Interview Deleted",
//                                        actionLabel = "Undo",
//                                        duration = SnackbarDuration.Short
//                                    )
//                                    if (undo == SnackbarResult.ActionPerformed) {
//                                        basicInterviewViewModel.onEvent(BasicInterviewEvent.UndoDelete(item))
//                                    }
//                                }
//                            },
//                            onEditClick = {
//                                navController.navigate("basic_interview_edit/${item.id}")
//                            }
//                        )
//                    }
//                    is SpecialInterviewItem -> {
//                        SpecialInterviewItemCard(
//                            specialInterview = item,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 8.dp),
//                            onDeleteClick = {
//                                specialInterviewViewModel.onEvent(SpecialInterviewEvent.Delete(item))
//                                scope.launch {
//                                    val undo = snackbarHostState.showSnackbar(
//                                        message = "Special Interview Deleted",
//                                        actionLabel = "Undo",
//                                        duration = SnackbarDuration.Short
//                                    )
//                                    if (undo == SnackbarResult.ActionPerformed) {
//                                        specialInterviewViewModel.onEvent(SpecialInterviewEvent.UndoDelete(item))
//                                    }
//                                }
//                            },
//                            onEditClick = {
//                                navController.navigate("special_interview_edit/${item.id}")
//                            }
//                        )
//                    }
//                    is WishListItem -> {
//                        WishlistItemCard(
//                            wishlistItem = item,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 8.dp),
//                            onDeleteClick = {
//                                wishListViewModel.onEvent(WishListEvent.Delete(item))
//                                scope.launch {
//                                    val undo = snackbarHostState.showSnackbar(
//                                        message = "Wishlist Item Deleted",
//                                        actionLabel = "Undo",
//                                        duration = SnackbarDuration.Short
//                                    )
//                                    if (undo == SnackbarResult.ActionPerformed) {
//                                        wishListViewModel.onEvent(WishListEvent.UndoDelete(item))
//                                    }
//                                }
//                            },
//                            onEditClick = {
//                                navController.navigate("${Screen.WishListUpdateScreen.route}?wishListId=${item.id}")
//                            }
//                        )
//                    }
//                }
//            }
//        }
//
//        // Loading Indicator
//        if (userState.isLoading) {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
//            }
//        }
//
//        // Snackbar for error messages
//        SnackbarHost(
//            hostState = snackbarHostState,
//            modifier = Modifier.align(Alignment.BottomCenter)
//        )
//
//        // Side Menu
//        if (isMenuOpen) {
//            HalfScreenMenu(
//                navController = navController,
//                isMenuOpen = remember { mutableStateOf(isMenuOpen) },
//                modifier = Modifier,
//                onDismiss = { isMenuOpen = false }
//            )
//        }
//    }
//
//    // Toggle menu
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp),
//        contentAlignment = Alignment.TopEnd
//    ) {
//        IconButton(onClick = { isMenuOpen = !isMenuOpen }) {
//            Icon(
//                imageVector = Icons.Default.Menu,
//                contentDescription = "Open Menu",
//                tint = Color.Black
//            )
//        }
//    }
//}



