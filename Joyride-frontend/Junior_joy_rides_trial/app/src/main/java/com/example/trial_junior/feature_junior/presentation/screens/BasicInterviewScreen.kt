package com.example.trial_junior.feature_junior.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.example.trial_junior.feature_junior.presentation.components.AppBottomNavigation
import com.example.trial_junior.feature_junior.presentation.components.HintInputField
import com.example.trial_junior.feature_junior.presentation.components.HalfScreenMenu
import com.example.trial_junior.feature_junior.presentation.util.Screen
import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterview_Update.BasicInterviewNewUpdateEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterview_Update.BasicInterviewNewUpdateViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BasicInterviewScreen(navController: NavHostController, viewModel: BasicInterviewNewUpdateViewModel = hiltViewModel()) {
    val (currentForm, setCurrentForm) = rememberSaveable { mutableStateOf("BasicInterview") }
    val defaultPadding = 16.dp
    val snackbarHostState = remember { SnackbarHostState() }
    val TAG = "BasicInterviewScreen"
    var isMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                BasicInterviewNewUpdateViewModel.UiEvent.Back -> {
                    Log.d(TAG, "Event: Back triggered")
                    navController.navigateUp()
                }
                BasicInterviewNewUpdateViewModel.UiEvent.SaveBasicInterview -> {
                    Log.d(TAG, "Event: SaveBasicInterview triggered")
                    snackbarHostState.showSnackbar("Successfully submitted!")
                    Toast.makeText(navController.context, "Interview saved!", Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                }
                is BasicInterviewNewUpdateViewModel.UiEvent.ShowSnackbar -> {
                    Log.d(TAG, "Event: ShowSnackbar triggered with message: ${event.message}")
                    snackbarHostState.showSnackbar("Submission failed: ${event.message}")
                    Toast.makeText(navController.context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController = navController)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(defaultPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Interview",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 5.dp),
                )

                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .padding(0.dp)
                )

                Spacer(modifier = Modifier.height(22.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Basic Interview",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { setCurrentForm("BasicInterview") }
                    )
                    Text(
                        text = "Talent Show",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { navController.navigate(Screen.SpecialInterviewScreen.route) }
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier.weight(1f).height(8.dp),
                        onClick = { },
                        colors = if (currentForm == "BasicInterview") {
                            ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
                        } else {
                            ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3))
                        }
                    ) {}
                    Button(
                        modifier = Modifier.weight(1f).height(8.dp),
                        onClick = { },
                        colors = if (currentForm == "TalentShow") {
                            ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
                        } else {
                            ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3))
                        }
                    ) {}
                }

                HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp))

                BasicInterview(viewModel = viewModel, snackbarHostState = snackbarHostState)
            }

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

@Composable
fun BasicInterview(viewModel: BasicInterviewNewUpdateViewModel, snackbarHostState: SnackbarHostState) {
    val state = viewModel.state.value
    val itemSpacing = 16.dp
    val TAG = "BasicInterviewScreen"

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(bottom = 30.dp) // Adjust based on AppBottomNavigation height
    ) {
        item {
            Text(
                modifier = Modifier.padding(bottom = itemSpacing).fillMaxWidth(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                text = "Read fairy tale with Ethiopis"
            )
        }

        item {
            HintInputField(
                value = state.basicInterview.childName,
                hint = "Child's Name",
                onValueChange = { newValue ->
                    Log.d(TAG, "Child's Name updated to: $newValue")
                    viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredChildName(newValue))
                },
                onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedChildNameFocus(it)) }
            )
            Spacer(Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.basicInterview.age.takeIf { it > 0 }?.toString() ?: "",
                hint = "Age",
                keyboardType = KeyboardType.Number,
                onValueChange = { newValue ->
                    Log.d(TAG, "Age updated to: $newValue")
                    newValue.toIntOrNull()?.let { age ->
                        viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredAge(age))
                    }
                },
                onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedAgeFocus(it)) }
            )
            Spacer(Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.basicInterview.guardianName,
                hint = "Guardian Name",
                onValueChange = { newValue ->
                    Log.d(TAG, "Guardian Name updated to: $newValue")
                    viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredGuardianName(newValue))
                },
                onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedGuardianNameFocus(it)) }
            )
            Spacer(Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.basicInterview.guardianEmail,
                hint = "Guardian's Email",
                keyboardType = KeyboardType.Email,
                onValueChange = { newValue ->
                    Log.d(TAG, "Guardian Email updated to: $newValue")
                    viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredGuardianEmail(newValue))
                },
                onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedGuardianEmailFocus(it)) }
            )
            Spacer(Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = if (state.basicInterview.guardianPhone > 0) state.basicInterview.guardianPhone.toString() else "",
                hint = "Phone Number",
                keyboardType = KeyboardType.Phone,
                onValueChange = { newValue ->
                    Log.d(TAG, "Guardian Phone updated to: $newValue")
                    newValue.toLongOrNull()?.let { phone ->
                        viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredGuardianPhone(phone))
                    }
                },
                onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedGuardianPhoneFocus(it)) }
            )
            Spacer(Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.basicInterview.specialRequests,
                hint = "Special Requests",
                keyboardType = KeyboardType.Text,
                onValueChange = { newValue ->
                    Log.d(TAG, "Special Requests updated to: $newValue")
                    viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredSpecialRequests(newValue))
                },
                onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedSpecialRequestsFocus(it)) }
            )
            Spacer(Modifier.height(itemSpacing))
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = itemSpacing),
                onClick = {
                    Log.d(TAG, "Submit Request clicked. Form values: " +
                            "childName=${state.basicInterview.childName}, " +
                            "age=${state.basicInterview.age}, " +
                            "guardianName=${state.basicInterview.guardianName}, " +
                            "guardianEmail=${state.basicInterview.guardianEmail}, " +
                            "guardianPhone=${state.basicInterview.guardianPhone}, " +
                            "specialRequests=${state.basicInterview.specialRequests}")
                    viewModel.onEvent(BasicInterviewNewUpdateEvent.SaveBasicInterview)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
            ) {
                Text("Submit Request")
            }
        }
    }
}




//package com.example.trial_junior.feature_junior.presentation.screens
//
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.*
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.navigation.NavController
//import com.example.trial_junior.feature_junior.presentation.util.Screen
//import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterview_Update.BasicInterviewNewUpdateEvent
//import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterview_Update.BasicInterviewNewUpdateViewModel
//import com.example.trial_junior.feature_junior.presentation.components.HintInputField
//import kotlinx.coroutines.flow.collectLatest
//
//@Composable
//fun BasicInterviewScreen(navController: NavController, viewModel: BasicInterviewNewUpdateViewModel = hiltViewModel()) {
//    val (currentForm, setCurrentForm) = rememberSaveable { mutableStateOf("BasicInterview") }
//    val defaultPadding = 16.dp
//    val snackbarHostState = remember { SnackbarHostState() }
//    val TAG = "BasicInterviewScreen"
//
//    LaunchedEffect(key1 = true) {
//        viewModel.eventFlow.collectLatest { event ->
//            when (event) {
//                BasicInterviewNewUpdateViewModel.UiEvent.Back -> {
//                    Log.d(TAG, "Event: Back triggered")
//                    navController.navigateUp()
//                }
//                BasicInterviewNewUpdateViewModel.UiEvent.SaveBasicInterview -> {
//                    Log.d(TAG, "Event: SaveBasicInterview triggered")
//                    snackbarHostState.showSnackbar("Successfully submitted!")
//                    Toast.makeText(navController.context, "Interview saved!", Toast.LENGTH_SHORT).show()
//                    navController.navigateUp()
//                }
//                is BasicInterviewNewUpdateViewModel.UiEvent.ShowSnackbar -> {
//                    Log.d(TAG, "Event: ShowSnackbar triggered with message: ${event.message}")
//                    snackbarHostState.showSnackbar("Submission failed: ${event.message}")
//                    Toast.makeText(navController.context, event.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Interview",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.Start)
//                .padding(vertical = 5.dp),
//        )
//
//        Divider(
//            color = Color.Gray,
//            thickness = 1.dp,
//            modifier = Modifier
//                .padding(vertical = 8.dp)
//                .padding(0.dp)
//        )
//
//        Spacer(modifier = Modifier.height(22.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(8.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                text = "Basic Interview",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = Color.Black,
//                modifier = Modifier.clickable { setCurrentForm("BasicInterview") }
//            )
//            Text(
//                text = "Talent Show",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = Color.Black,
//                modifier = Modifier.clickable { navController.navigate(Screen.SpecialInterviewScreen.route) }
//            )
//        }
//
//        Row(modifier = Modifier.fillMaxWidth()) {
//            Button(
//                modifier = Modifier.weight(1f).height(8.dp),
//                onClick = { },
//                colors = if (currentForm == "BasicInterview") {
//                    ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
//                } else {
//                    ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3))
//                }
//            ) {}
//            Button(
//                modifier = Modifier.weight(1f).height(8.dp),
//                onClick = { },
//                colors = if (currentForm == "TalentShow") {
//                    ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
//                } else {
//                    ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3))
//                }
//            ) {}
//        }
//
//        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp))
//
//        BasicInterview(viewModel = viewModel, snackbarHostState = snackbarHostState)
//    }
//}
//
//@Composable
//fun BasicInterview(viewModel: BasicInterviewNewUpdateViewModel, snackbarHostState: SnackbarHostState) {
//    val state = viewModel.state.value
//    val itemSpacing = 16.dp
//    val TAG = "BasicInterviewScreen"
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
//    ) { paddingValues ->
//        LazyColumn(
//            modifier = Modifier
//                .background(Color.White)
//                .fillMaxSize()
//                .padding(horizontal = 16.dp, vertical = 8.dp)
//                .padding(paddingValues)
//        ) {
//            item {
//                Text(
//                    modifier = Modifier.padding(bottom = itemSpacing).fillMaxWidth(),
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 24.sp,
//                    text = "Read fairy tale with Ethiopis"
//                )
//            }
//
//            item {
//                HintInputField(
//                    value = state.basicInterview.childName,
//                    hint = "Child's Name",
//                    onValueChange = { newValue ->
//                        Log.d(TAG, "Child's Name updated to: $newValue")
//                        viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredChildName(newValue))
//                    },
//                    onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedChildNameFocus(it)) }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.basicInterview.age.takeIf { it > 0 }?.toString() ?: "",
//                    hint = "Age",
//                    keyboardType = KeyboardType.Number,
//                    onValueChange = { newValue ->
//                        Log.d(TAG, "Age updated to: $newValue")
//                        newValue.toIntOrNull()?.let { age ->
//                            viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredAge(age))
//                        }
//                    },
//                    onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedAgeFocus(it)) }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.basicInterview.guardianName,
//                    hint = "Guardian Name",
//                    onValueChange = { newValue ->
//                        Log.d(TAG, "Guardian Name updated to: $newValue")
//                        viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredGuardianName(newValue))
//                    },
//                    onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedGuardianNameFocus(it)) }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.basicInterview.guardianEmail,
//                    hint = "Guardian's Email",
//                    keyboardType = KeyboardType.Email,
//                    onValueChange = { newValue ->
//                        Log.d(TAG, "Guardian Email updated to: $newValue")
//                        viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredGuardianEmail(newValue))
//                    },
//                    onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedGuardianEmailFocus(it)) }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = if (state.basicInterview.guardianPhone > 0) state.basicInterview.guardianPhone.toString() else "",
//                    hint = "Phone Number",
//                    keyboardType = KeyboardType.Phone,
//                    onValueChange = { newValue ->
//                        Log.d(TAG, "Guardian Phone updated to: $newValue")
//                        newValue.toLongOrNull()?.let { phone ->
//                            viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredGuardianPhone(phone))
//                        }
//                    },
//                    onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedGuardianPhoneFocus(it)) }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.basicInterview.specialRequests,
//                    hint = "Special Requests",
//                    keyboardType = KeyboardType.Text,
//                    onValueChange = { newValue ->
//                        Log.d(TAG, "Special Requests updated to: $newValue")
//                        viewModel.onEvent(BasicInterviewNewUpdateEvent.EnteredSpecialRequests(newValue))
//                    },
//                    onFocusChange = { viewModel.onEvent(BasicInterviewNewUpdateEvent.ChangedSpecialRequestsFocus(it)) }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                Button(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = itemSpacing),
//                    onClick = {
//                        Log.d(TAG, "Submit Request clicked. Form values: " +
//                                "childName=${state.basicInterview.childName}, " +
//                                "age=${state.basicInterview.age}, " +
//                                "guardianName=${state.basicInterview.guardianName}, " +
//                                "guardianEmail=${state.basicInterview.guardianEmail}, " +
//                                "guardianPhone=${state.basicInterview.guardianPhone}, " +
//                                "specialRequests=${state.basicInterview.specialRequests}")
//                        viewModel.onEvent(BasicInterviewNewUpdateEvent.SaveBasicInterview)
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
//                ) {
//                    Text("Submit Request")
//                }
//            }
//        }
//    }
//}


