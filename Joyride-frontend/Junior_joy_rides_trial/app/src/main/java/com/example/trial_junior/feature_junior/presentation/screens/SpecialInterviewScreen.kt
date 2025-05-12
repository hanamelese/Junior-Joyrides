package com.example.trial_junior.feature_junior.presentation.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.trial_junior.feature_junior.presentation.components.AppBottomNavigation
import com.example.trial_junior.feature_junior.presentation.components.HalfScreenMenu
import com.example.trial_junior.feature_junior.presentation.util.Screen
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterview_Update.SpecialInterviewNewUpdateEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterview_Update.SpecialInterviewNewUpdateViewModel
import com.example.trial_junior.feature_junior.presentation.components.HintInputField
import kotlinx.coroutines.launch

@Composable
fun SpecialInterviewScreen(navController: NavHostController, viewModel: SpecialInterviewNewUpdateViewModel = hiltViewModel()) {
    val (currentForm, setCurrentForm) = rememberSaveable { mutableStateOf("TalentShow") }
    val defaultPadding = 16.dp
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                SpecialInterviewNewUpdateViewModel.UiEvent.SaveSpecialInterview -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Form submitted successfully!")
                        Toast.makeText(context, "Form submitted successfully!", Toast.LENGTH_SHORT).show()
                        navController.navigateUp()
                    }
                }
                is SpecialInterviewNewUpdateViewModel.UiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Submission failed: ${event.message}")
                        Toast.makeText(context, "Submission failed: ${event.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                SpecialInterviewNewUpdateViewModel.UiEvent.Back -> {
                    navController.navigateUp()
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
                    .padding(16.dp),
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
                        modifier = Modifier.clickable { navController.navigate(Screen.BasicInterviewScreen.route) }
                    )
                    Text(
                        text = "Talent Show",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { setCurrentForm("TalentShow") }
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

                SpecialInterview(viewModel = viewModel, snackbarHostState = snackbarHostState, navController = navController)
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
fun SpecialInterview(
    viewModel: SpecialInterviewNewUpdateViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    navController: NavHostController
) {
    val context = LocalContext.current
    val state = viewModel.state.value

    val itemSpacing = 16.dp

    // File picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
        uri?.let {
            val uriString = it.toString()
            viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredVideoUrl(uriString))
        }
    }

    val openDrivePicker = {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "video/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        launcher.launch(intent)
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(bottom = 30.dp) // Adjust based on AppBottomNavigation height
    ) {
        item {
            Text(
                text = "Enhance your child’s talent",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = itemSpacing)
            )
        }

        item {
            Text(
                text = if (state.specialInterview.videoUrl.isEmpty()) "No video selected" else state.specialInterview.videoUrl,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Button(
                onClick = openDrivePicker,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .height(100.dp)
                    .padding(start = 56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Videocam,
                    contentDescription = "Select Video",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.specialInterview.childName,
                hint = "Child's Name",
                onValueChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredChildName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedChildNameFocus(it))
                }
            )
            Spacer(modifier = Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.specialInterview.age.takeIf { it > 0 }?.toString() ?: "",
                hint = "Age",
                keyboardType = KeyboardType.Number,
                onValueChange = { input ->
                    if (input.isEmpty() || input.all { it.isDigit() }) {
                        val parsed = input.toIntOrNull() ?: 0
                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredAge(parsed))
                    }
                },
                onFocusChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedAgeFocus(it))
                }
            )
            Spacer(modifier = Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.specialInterview.guardianName,
                hint = "Guardian Name",
                onValueChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredGuardianName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedGuardianNameFocus(it))
                }
            )
            Spacer(modifier = Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.specialInterview.guardianEmail,
                hint = "Email",
                keyboardType = KeyboardType.Email,
                onValueChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredGuardianEmail(it))
                },
                onFocusChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedGuardianEmailFocus(it))
                }
            )
            Spacer(modifier = Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.specialInterview.guardianPhone.takeIf { it > 0 }?.toString() ?: "",
                hint = "Phone Number",
                keyboardType = KeyboardType.Phone,
                onValueChange = { input ->
                    val filtered = input.filter { it.isDigit() }
                    val parsed = filtered.toLongOrNull() ?: 0L
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredGuardianPhone(parsed))
                },
                onFocusChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedGuardianPhoneFocus(it))
                }
            )
            Spacer(modifier = Modifier.height(itemSpacing))
        }

        item {
            HintInputField(
                value = state.specialInterview.specialRequests,
                hint = "Special Requirements",
                onValueChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredSpecialRequests(it))
                },
                onFocusChange = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedSpecialRequestsFocus(it))
                }
            )
            Spacer(modifier = Modifier.height(itemSpacing))
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = itemSpacing),
                onClick = {
                    viewModel.onEvent(SpecialInterviewNewUpdateEvent.SaveSpecialInterview)
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
//import android.content.Intent
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.Videocam
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import com.example.trial_junior.feature_junior.presentation.util.Screen
//import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterview_Update.SpecialInterviewNewUpdateEvent
//import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterview_Update.SpecialInterviewNewUpdateViewModel
//import com.example.trial_junior.feature_junior.presentation.components.HintInputField
//import kotlinx.coroutines.launch
//
//@Composable
//fun SpecialInterviewScreen(navController: NavHostController, viewModel: SpecialInterviewNewUpdateViewModel = hiltViewModel()) {
//    val (currentForm, setCurrentForm) = rememberSaveable { mutableStateOf("TalentShow") }
//    val defaultPadding = 16.dp
//    val snackbarHostState = remember { SnackbarHostState() }
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        viewModel.eventFlow.collect { event ->
//            when (event) {
//                SpecialInterviewNewUpdateViewModel.UiEvent.SaveSpecialInterview -> {
//                    scope.launch {
//                        snackbarHostState.showSnackbar("Form submitted successfully!")
//                        Toast.makeText(context, "Form submitted successfully!", Toast.LENGTH_SHORT).show()
//                        navController.navigateUp()
//                    }
//                }
//                is SpecialInterviewNewUpdateViewModel.UiEvent.ShowSnackbar -> {
//                    scope.launch {
//                        snackbarHostState.showSnackbar("Submission failed: ${event.message}")
//                        Toast.makeText(context, "Submission failed: ${event.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                SpecialInterviewNewUpdateViewModel.UiEvent.Back -> {
//                    navController.navigateUp()
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
//                modifier = Modifier.clickable { navController.navigate(Screen.BasicInterviewScreen.route) }
//            )
//            Text(
//                text = "Talent Show",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = Color.Black,
//                modifier = Modifier.clickable { setCurrentForm("TalentShow") }
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
//        SpecialInterview(viewModel = viewModel, snackbarHostState = snackbarHostState, navController = navController)
//    }
//}
//
//@Composable
//fun SpecialInterview(
//    viewModel: SpecialInterviewNewUpdateViewModel = hiltViewModel(),
//    snackbarHostState: SnackbarHostState,
//    navController: NavHostController
//) {
//    val context = LocalContext.current
//    val state = viewModel.state.value
//
//    val itemSpacing = 16.dp
//
//    // File picker launcher
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        val uri = result.data?.data
//        uri?.let {
//            val uriString = it.toString()
//            viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredVideoUrl(uriString))
//        }
//    }
//
//    val openDrivePicker = {
//        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
//            type = "video/*"
//            addCategory(Intent.CATEGORY_OPENABLE)
//        }
//        launcher.launch(intent)
//    }
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
//                    text = "Enhance your child’s talent",
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(bottom = itemSpacing)
//                )
//            }
//
//            item {
//                Text(
//                    text = if (state.specialInterview.videoUrl.isEmpty()) "No video selected" else state.specialInterview.videoUrl,
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//
//                Button(
//                    onClick = openDrivePicker,
//                    modifier = Modifier
//                        .fillMaxWidth(0.65f)
//                        .height(100.dp)
//                        .padding(start = 56.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.LightGray
//                    ),
//                    shape = RoundedCornerShape(15.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Outlined.Videocam,
//                        contentDescription = "Select Video",
//                        modifier = Modifier.size(32.dp),
//                        tint = Color.Black
//                    )
//                }
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.specialInterview.childName,
//                    hint = "Child's Name",
//                    onValueChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredChildName(it))
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedChildNameFocus(it))
//                    }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.specialInterview.age.takeIf { it > 0 }?.toString() ?: "",
//                    hint = "Age",
//                    keyboardType = KeyboardType.Number,
//                    onValueChange = { input ->
//                        if (input.isEmpty() || input.all { it.isDigit() }) {
//                            val parsed = input.toIntOrNull() ?: 0
//                            viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredAge(parsed))
//                        }
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedAgeFocus(it))
//                    }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.specialInterview.guardianName,
//                    hint = "Guardian Name",
//                    onValueChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredGuardianName(it))
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedGuardianNameFocus(it))
//                    }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.specialInterview.guardianEmail,
//                    hint = "Email",
//                    keyboardType = KeyboardType.Email,
//                    onValueChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredGuardianEmail(it))
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedGuardianEmailFocus(it))
//                    }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.specialInterview.guardianPhone.takeIf { it > 0 }?.toString() ?: "",
//                    hint = "Phone Number",
//                    keyboardType = KeyboardType.Phone,
//                    onValueChange = { input ->
//                        val filtered = input.filter { it.isDigit() }
//                        val parsed = filtered.toLongOrNull() ?: 0L
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredGuardianPhone(parsed))
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedGuardianPhoneFocus(it))
//                    }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.specialInterview.specialRequests,
//                    hint = "Special Requirements",
//                    onValueChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.EnteredSpecialRequests(it))
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.ChangedSpecialRequestsFocus(it))
//                    }
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
//                        viewModel.onEvent(SpecialInterviewNewUpdateEvent.SaveSpecialInterview)
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
//                ) {
//                    Text("Submit Request")
//                }
//            }
//        }
//    }
//}