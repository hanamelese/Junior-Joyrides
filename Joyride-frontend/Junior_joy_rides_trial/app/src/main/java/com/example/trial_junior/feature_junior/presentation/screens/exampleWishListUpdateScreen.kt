package com.example.trial_junior.feature_junior.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trial_junior.core.util.ContentDescriptions
import com.example.trial_junior.core.util.ListStrings
import com.example.trial_junior.feature_junior.presentation.components.HintInputField
import com.example.trial_junior.feature_junior.presentation.viewModels.WishList_Update.WishListNewUpdateEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.WishList_Update.WishListNewUpdateViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListNewUpdateScreen(
    navController: NavController,
    viewModel: WishListNewUpdateViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val topBarHeight = if(isPortrait) 64.dp else 0.dp
    val horizontalPadding = 16.dp
    val verticalPadding = if(isPortrait) 16.dp else 2.dp


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                WishListNewUpdateViewModel.UiEvent.Back -> {
                    navController.navigateUp()
                }

                WishListNewUpdateViewModel.UiEvent.SaveWishList -> {
                    navController.navigateUp()
                }

                is WishListNewUpdateViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!isPortrait) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(WishListNewUpdateEvent.SaveWishList)
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = ContentDescriptions.SUBMIT_ITEM,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        topBar = {
            if (isPortrait) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = ListStrings.INVITATION_LIST,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineMedium,
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
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(WishListNewUpdateEvent.Back)
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = ContentDescriptions.BACK,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {},
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                )

            }
        },
        bottomBar = {
            if (isPortrait) {
                BottomAppBar(
                    actions = {
//                   //TODO
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                viewModel.onEvent(WishListNewUpdateEvent.SaveWishList)
                            },
                            shape = CircleShape,
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = ContentDescriptions.SUBMIT_ITEM,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) { padding ->

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Column(
            modifier = Modifier
                .padding(top = topBarHeight)
                .fillMaxSize()
            ) {
                HintInputField (
                    value = state.wishlist.childName,
                    hint = "Child's Name",
                    onValueChange = { viewModel.onEvent(WishListNewUpdateEvent.EnteredChildName(it)) },
                    onFocusChange = { viewModel.onEvent(WishListNewUpdateEvent.ChangedChildNameFocus(it)) }
                )
                HintInputField(
                    value = state.wishlist.age.toString(),
                    hint = "Age",
                    keyboardType = KeyboardType.Number,
                    onValueChange = { value ->
                        val age = value.toIntOrNull() ?: 0
                        viewModel.onEvent(WishListNewUpdateEvent.EnteredAge(age))
                    },
                    onFocusChange = { viewModel.onEvent(WishListNewUpdateEvent.ChangedAgeFocus(it)) }
                )
                HintInputField(
                    value = state.wishlist.guardianEmail,
                    hint = "Guardian's Email",
                    keyboardType = KeyboardType.Email,
                    onValueChange = { viewModel.onEvent(WishListNewUpdateEvent.EnteredGuardianEmail(it)) },
                    onFocusChange = { viewModel.onEvent(WishListNewUpdateEvent.ChangedGuardianEmailFocus(it)) }
                )
                HintInputField(
                    value = state.wishlist.date,
                    hint = "Child's Birthday date",
                    keyboardType = KeyboardType.Text,
                    onValueChange = { viewModel.onEvent(WishListNewUpdateEvent.EnteredDate(it)) },
                    onFocusChange = { viewModel.onEvent(WishListNewUpdateEvent.ChangedDateFocus(it)) }
                )
                HintInputField(
                    value = state.wishlist.imageUrl,
                    hint = "birthday pic googleDrive link",
                    keyboardType = KeyboardType.Text,
                    onValueChange = { viewModel.onEvent(WishListNewUpdateEvent.EnteredImageUrl(it)) },
                    onFocusChange = { viewModel.onEvent(WishListNewUpdateEvent.ChangedImageUrlFocus(it)) }
                )
                HintInputField(
                    value = state.wishlist.specialRequests,
                    hint = "Special Requests",
                    keyboardType = KeyboardType.Text,
                    onValueChange = { viewModel.onEvent(WishListNewUpdateEvent.EnteredSpecialRequests(it)) },
                    onFocusChange = { viewModel.onEvent(WishListNewUpdateEvent.ChangedSpecialRequestsFocus(it)) }
                )
                // Add other fields (date, imageUrl, specialRequests) similarly
            }
        }

    }
}
