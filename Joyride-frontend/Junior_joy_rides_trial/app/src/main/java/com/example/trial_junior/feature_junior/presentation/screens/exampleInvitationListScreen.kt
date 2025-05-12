package com.example.trial_junior.feature_junior.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trial_junior.core.util.ContentDescriptions
import com.example.trial_junior.core.util.ListStrings
import com.example.trial_junior.feature_junior.presentation.components.BasicInterviewItemCardAdmin
import com.example.trial_junior.feature_junior.presentation.components.InvitationItemCardAdmin
import com.example.trial_junior.feature_junior.presentation.components.SortingDrawerOptions
import com.example.trial_junior.feature_junior.presentation.components.SpecialInterviewItemCardAdmin
import com.example.trial_junior.feature_junior.presentation.components.WishlistItemCardAdmin
import com.example.trial_junior.feature_junior.presentation.util.Screen
import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterviewEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterviewListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.WishListEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.WishListViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExampleScreen(
    navController: NavController,
    invitationViewModel: InvitationListViewModel = hiltViewModel(),
    wishlistViewModel: WishListViewModel = hiltViewModel(),
    basicInterviewViewModel: BasicInterviewListViewModel = hiltViewModel(),
    specialInterviewViewModel: SpecialInterviewListViewModel = hiltViewModel()
) {
    val invitationState = invitationViewModel.state.value
    val wishlistState = wishlistViewModel.state.value
    val basicInterviewState = basicInterviewViewModel.state.value
    val specialInterviewState = specialInterviewViewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        invitationViewModel.getItems()
        wishlistViewModel.getItems()
        basicInterviewViewModel.getItems()
        specialInterviewViewModel.getItems()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier.fillMaxWidth(0.65f)) {
                ModalDrawerSheet {
                    Text(
                        text = ListStrings.SORT_BY,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 34.sp,
                        lineHeight = 38.sp
                    )
                    Divider()
                    SortingDrawerOptions(
                        invitationItemOrder = invitationViewModel.invitationItemOrder.value,
                        onOrderChange = { order ->
                            invitationViewModel.onEvent(InvitationListEvent.Sort(order))
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.WishListUpdateScreen.route)
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = ContentDescriptions.ADD,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = ListStrings.INVITATION_LIST,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        scrolledContainerColor = MaterialTheme.colorScheme.primary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {},
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = ContentDescriptions.SORTING_MENU
                            )
                        }
                    },
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Basic Interviews Section
                    Text(
                        text = "Basic Interviews",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, top = 80.dp, bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        items(basicInterviewState.items) { basicInterview ->
                            BasicInterviewItemCardAdmin(
                                basicInterview = basicInterview,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                onDeleteClick = {
                                    basicInterviewViewModel.onEvent(
                                        BasicInterviewEvent.Delete(
                                            basicInterview
                                        )
                                    )
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = "Basic Interview Deleted",
                                            actionLabel = ListStrings.UNDO,
                                            duration = SnackbarDuration.Short
                                        )
                                        if (undo == SnackbarResult.ActionPerformed) {
                                            basicInterviewViewModel.onEvent(
                                                BasicInterviewEvent.UndoDelete(
                                                    basicInterview
                                                )
                                            )
                                        }
                                    }
                                },
                                onEditClick = {
                                    // Navigate to edit basic interview screen
                                    // navController.navigate("basic_interview_edit/${basicInterview.id}")
                                },
                                onToggleHostedClick = {
                                    basicInterviewViewModel.onEvent(
                                        BasicInterviewEvent.ToggleHosted(
                                            basicInterview
                                        )
                                    )
                                }
                            )
                        }
                    }

                    // Special Interviews Section
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Special Interviews",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        items(specialInterviewState.items) { specialInterview ->
                            SpecialInterviewItemCardAdmin(
                                specialInterview = specialInterview,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                onDeleteClick = {
                                    specialInterviewViewModel.onEvent(
                                        SpecialInterviewEvent.Delete(
                                            specialInterview
                                        )
                                    )
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = "Special Interview Deleted",
                                            actionLabel = ListStrings.UNDO,
                                            duration = SnackbarDuration.Short
                                        )
                                        if (undo == SnackbarResult.ActionPerformed) {
                                            specialInterviewViewModel.onEvent(
                                                SpecialInterviewEvent.UndoDelete(
                                                    specialInterview
                                                )
                                            )
                                        }
                                    }
                                },
                                onEditClick = {
                                    // Navigate to edit special interview screen
                                    // navController.navigate("special_interview_edit/${specialInterview.id}")
                                },
                                onToggleHostedClick = {
                                    specialInterviewViewModel.onEvent(
                                        SpecialInterviewEvent.ToggleHosted(
                                            specialInterview
                                        )
                                    )
                                }
                            )
                        }
                    }

                    // Invitations Section
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Invitations",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        items(invitationState.items) { invitation ->
                            InvitationItemCardAdmin(
                                invitation = invitation,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                onDeleteClick = {
                                    invitationViewModel.onEvent(
                                        InvitationListEvent.Delete(
                                            invitation
                                        )
                                    )
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = ListStrings.INVITATION_ITEM_DELETED,
                                            actionLabel = ListStrings.UNDO,
                                            duration = SnackbarDuration.Short
                                        )
                                        if (undo == SnackbarResult.ActionPerformed) {
                                            invitationViewModel.onEvent(
                                                InvitationListEvent.UndoDelete(
                                                    invitation
                                                )
                                            )
                                        }
                                    }
                                },
                                onEditClick = {
                                    // Navigate to edit invitation screen
                                    // navController.navigate("invitation_edit/${invitation.id}")
                                },
                                onToggleHostedClick = {
                                    invitationViewModel.onEvent(
                                        InvitationListEvent.ToggleHosted(
                                            invitation
                                        )
                                    )
                                }
                            )
                        }
                    }

                    // Wishlist Section
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Wishlist",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        items(wishlistState.items) { wishlistItem ->
                            WishlistItemCardAdmin(
                                wishlistItem = wishlistItem,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                onDeleteClick = {
                                    wishlistViewModel.onEvent(WishListEvent.Delete(wishlistItem))
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = "Wishlist Item Deleted",
                                            actionLabel = ListStrings.UNDO,
                                            duration = SnackbarDuration.Short
                                        )
                                        if (undo == SnackbarResult.ActionPerformed) {
                                            wishlistViewModel.onEvent(
                                                WishListEvent.UndoDelete(
                                                    wishlistItem
                                                )
                                            )
                                        }
                                    }
                                },
                                onEditClick = {
                                     navController.navigate(
                                         Screen.WishListUpdateScreen.route + "?wishListId=${wishlistItem.id}"
                                     )
                                },
                                onToggleHostedClick = {
                                    wishlistViewModel.onEvent(
                                        WishListEvent.ToggleHosted(
                                            wishlistItem
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                // Loading Indicator
                if (invitationState.isLoading || wishlistState.isLoading || basicInterviewState.isLoading || specialInterviewState.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.semantics {
                                this.contentDescription = ContentDescriptions.LOADING_INDICATOR
                            }
                        )
                    }
                }

                // Error Message
                if (invitationState.error != null || wishlistState.error != null || basicInterviewState.error != null || specialInterviewState.error != null) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = invitationState.error ?: wishlistState.error ?: basicInterviewState.error ?: specialInterviewState.error ?: "Unknown Error",
                            fontSize = 30.sp,
                            lineHeight = 36.sp
                        )
                    }
                }
            }
        }
    }
}