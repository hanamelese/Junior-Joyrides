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
import androidx.compose.material.icons.outlined.PhotoCamera
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
import com.example.trial_junior.feature_junior.presentation.viewModels.WishList_Update.WishListNewUpdateEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.WishList_Update.WishListNewUpdateViewModel
import com.example.trial_junior.feature_junior.presentation.components.HintInputField
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun WishListScreen(navController: NavHostController, viewModel: WishListNewUpdateViewModel = hiltViewModel()) {
    val (currentForm, setCurrentForm) = rememberSaveable { mutableStateOf("wishlist") }
    val defaultPadding = 16.dp
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                WishListNewUpdateViewModel.UiEvent.SaveWishList -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Form submitted successfully!")
                        Toast.makeText(context, "Form submitted successfully!", Toast.LENGTH_SHORT).show()
                        navController.navigateUp()
                    }
                }
                is WishListNewUpdateViewModel.UiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Submission failed: ${event.message}")
                        Toast.makeText(context, "Submission failed: ${event.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                WishListNewUpdateViewModel.UiEvent.Back -> {
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
                    text = "Birthday",
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
                        text = "Invite Etopis",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { navController.navigate(Screen.InvitationScreen.route) }
                    )
                    Text(
                        text = "Wishlist",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { setCurrentForm("wishlist") }
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier.weight(1f).height(8.dp),
                        onClick = { },
                        colors = if (currentForm == "invitation") {
                            ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
                        } else {
                            ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3))
                        }
                    ) {}
                    Button(
                        modifier = Modifier.weight(1f).height(8.dp),
                        onClick = { },
                        colors = if (currentForm == "wishlist") {
                            ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
                        } else {
                            ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3))
                        }
                    ) {}
                }

                HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp))

                WishList(viewModel = viewModel, snackbarHostState = snackbarHostState, navController = navController)
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
fun WishList(
    viewModel: WishListNewUpdateViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    navController: NavHostController
) {
    val context = LocalContext.current
    val state = viewModel.state.value

    val itemSpacing = 16.dp

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
        uri?.let {
            val uriString = it.toString()
            viewModel.onEvent(WishListNewUpdateEvent.EnteredImageUrl(uriString))
        }
    }

    val openImagePicker = {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        launcher.launch(intent)
    }

    val calendar = remember { Calendar.getInstance() }

    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedDate = "${year}-${month + 1}-${day}"
                viewModel.onEvent(WishListNewUpdateEvent.EnteredDate(selectedDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val screenHeight = maxHeight

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 56.dp) // Adjust based on AppBottomNavigation height
        ) {
            item {
                Text(
                    text = "Join the Birthday Wishlist",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = itemSpacing)
                )
            }

            item {
                Text(
                    text = "Upload your child’s photo to be featured on our TV show this week!",
                    modifier = Modifier.padding(bottom = itemSpacing)
                )
            }

            item {
                Text(
                    text = if (state.wishlist.imageUrl.isEmpty()) "No image selected" else state.wishlist.imageUrl,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Button(
                    onClick = openImagePicker,
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
                        imageVector = Icons.Outlined.PhotoCamera,
                        contentDescription = "Select Image",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Spacer(modifier = Modifier.height(itemSpacing))
            }

            item {
                HintInputField(
                    value = state.wishlist.childName,
                    hint = "Child's Name",
                    onValueChange = {
                        viewModel.onEvent(WishListNewUpdateEvent.EnteredChildName(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(WishListNewUpdateEvent.ChangedChildNameFocus(it))
                    }
                )
                Spacer(modifier = Modifier.height(itemSpacing))
            }

            item {
                HintInputField(
                    value = state.wishlist.guardianEmail,
                    hint = "Guardian Email",
                    keyboardType = KeyboardType.Email,
                    onValueChange = {
                        viewModel.onEvent(WishListNewUpdateEvent.EnteredGuardianEmail(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(WishListNewUpdateEvent.ChangedGuardianEmailFocus(it))
                    }
                )
                Spacer(modifier = Modifier.height(itemSpacing))
            }

            item {
                HintInputField(
                    value = state.wishlist.age.takeIf { it > 0 }?.toString() ?: "",
                    hint = "Age",
                    keyboardType = KeyboardType.Number,
                    onValueChange = { input ->
                        if (input.isEmpty() || input.all { it.isDigit() }) {
                            val parsed = input.toIntOrNull() ?: 0
                            viewModel.onEvent(WishListNewUpdateEvent.EnteredAge(parsed))
                        }
                    },
                    onFocusChange = {
                        viewModel.onEvent(WishListNewUpdateEvent.ChangedAgeFocus(it))
                    }
                )
                Spacer(modifier = Modifier.height(itemSpacing))
            }

            item {
                TextField(
                    value = state.wishlist.date,
                    onValueChange = {},
                    placeholder = { Text("Date of Celebration", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() },
                    enabled = false,
                    shape = RoundedCornerShape(percent = 30),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE7E7E7),
                        unfocusedContainerColor = Color(0xFFE7E7E7),
                        disabledContainerColor = Color(0xFFE7E7E7),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(itemSpacing))
            }

            item {
                HintInputField(
                    value = state.wishlist.specialRequests,
                    hint = "Special Requirements",
                    onValueChange = {
                        viewModel.onEvent(WishListNewUpdateEvent.EnteredSpecialRequests(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(WishListNewUpdateEvent.ChangedSpecialRequestsFocus(it))
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
                        viewModel.onEvent(WishListNewUpdateEvent.SaveWishList)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
                ) {
                    Text("Submit Request")
                }
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
//import androidx.compose.material.icons.outlined.PhotoCamera
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
//import com.example.trial_junior.feature_junior.presentation.viewModels.WishList_Update.WishListNewUpdateEvent
//import com.example.trial_junior.feature_junior.presentation.viewModels.WishList_Update.WishListNewUpdateViewModel
//import com.example.trial_junior.feature_junior.presentation.components.HintInputField
//import kotlinx.coroutines.launch
//import java.util.Calendar
//
//@Composable
//fun WishListScreen(navController: NavHostController, viewModel: WishListNewUpdateViewModel = hiltViewModel()) {
//    val (currentForm, setCurrentForm) = rememberSaveable { mutableStateOf("wishlist") }
//    val defaultPadding = 16.dp
//    val snackbarHostState = remember { SnackbarHostState() }
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        viewModel.eventFlow.collect { event ->
//            when (event) {
//                WishListNewUpdateViewModel.UiEvent.SaveWishList -> {
//                    scope.launch {
//                        snackbarHostState.showSnackbar("Form submitted successfully!")
//                        Toast.makeText(context, "Form submitted successfully!", Toast.LENGTH_SHORT).show()
//                        navController.navigateUp()
//                    }
//                }
//                is WishListNewUpdateViewModel.UiEvent.ShowSnackbar -> {
//                    scope.launch {
//                        snackbarHostState.showSnackbar("Submission failed: ${event.message}")
//                        Toast.makeText(context, "Submission failed: ${event.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                WishListNewUpdateViewModel.UiEvent.Back -> {
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
//            text = "Birthday",
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
//                text = "Invite Etopis",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = Color.Black,
//                modifier = Modifier.clickable { navController.navigate(Screen.InvitationScreen.route) }
//            )
//            Text(
//                text = "Wishlist",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = Color.Black,
//                modifier = Modifier.clickable { setCurrentForm("wishlist") }
//            )
//        }
//        Row(modifier = Modifier.fillMaxWidth()) {
//            Button(
//                modifier = Modifier.weight(1f).height(8.dp),
//                onClick = { },
//                colors = if (currentForm == "invitation") {
//                    ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
//                } else {
//                    ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3))
//                }
//            ) {}
//            Button(
//                modifier = Modifier.weight(1f).height(8.dp),
//                onClick = { },
//                colors = if (currentForm == "wishlist") {
//                    ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
//                } else {
//                    ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3))
//                }
//            ) {}
//        }
//
//        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp))
//
//        WishList(viewModel = viewModel, snackbarHostState = snackbarHostState, navController = navController)
//    }
//}
//
//@Composable
//fun WishList(
//    viewModel: WishListNewUpdateViewModel = hiltViewModel(),
//    snackbarHostState: SnackbarHostState,
//    navController: NavHostController
//) {
//    val context = LocalContext.current
//    val state = viewModel.state.value
//
//    val itemSpacing = 16.dp
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        val uri = result.data?.data
//        uri?.let {
//            val uriString = it.toString()
//            viewModel.onEvent(WishListNewUpdateEvent.EnteredImageUrl(uriString))
//        }
//    }
//
//    val openImagePicker = {
//        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
//            type = "image/*"
//            addCategory(Intent.CATEGORY_OPENABLE)
//        }
//        launcher.launch(intent)
//    }
//
//    val calendar = remember { Calendar.getInstance() }
//
//    val datePickerDialog = remember {
//        android.app.DatePickerDialog(
//            context,
//            { _, year, month, day ->
//                val selectedDate = "${year}-${month + 1}-${day}"
//                viewModel.onEvent(WishListNewUpdateEvent.EnteredDate(selectedDate))
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        )
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
//                    text = "Join the Birthday Wishlist",
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(bottom = itemSpacing)
//                )
//            }
//
//            item {
//                Text(
//                    text = "Upload your child’s photo to be featured on our TV show this week!",
//                    modifier = Modifier.padding(bottom = itemSpacing)
//                )
//            }
//
//            item {
//                Text(
//                    text = if (state.wishlist.imageUrl.isEmpty()) "No image selected" else state.wishlist.imageUrl,
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//
//                Button(
//                    onClick = openImagePicker,
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
//                        imageVector = Icons.Outlined.PhotoCamera,
//                        contentDescription = "Select Image",
//                        modifier = Modifier.size(32.dp),
//                        tint = Color.Black
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                }
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.wishlist.childName,
//                    hint = "Child's Name",
//                    onValueChange = {
//                        viewModel.onEvent(WishListNewUpdateEvent.EnteredChildName(it))
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(WishListNewUpdateEvent.ChangedChildNameFocus(it))
//                    }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.wishlist.guardianEmail,
//                    hint = "Guardian Email",
//                    keyboardType = KeyboardType.Email,
//                    onValueChange = {
//                        viewModel.onEvent(WishListNewUpdateEvent.EnteredGuardianEmail(it))
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(WishListNewUpdateEvent.ChangedGuardianEmailFocus(it))
//                    }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.wishlist.age.takeIf { it > 0 }?.toString() ?: "",
//                    hint = "Age",
//                    keyboardType = KeyboardType.Number,
//                    onValueChange = { input ->
//                        if (input.isEmpty() || input.all { it.isDigit() }) {
//                            val parsed = input.toIntOrNull() ?: 0
//                            viewModel.onEvent(WishListNewUpdateEvent.EnteredAge(parsed))
//                        }
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(WishListNewUpdateEvent.ChangedAgeFocus(it))
//                    }
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                TextField(
//                    value = state.wishlist.date,
//                    onValueChange = {},
//                    placeholder = { Text("Date of Celebration", color = Color.Gray) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { datePickerDialog.show() },
//                    enabled = false,
//                    shape = RoundedCornerShape(percent = 30),
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = Color(0xFFE7E7E7),
//                        unfocusedContainerColor = Color(0xFFE7E7E7),
//                        disabledContainerColor = Color(0xFFE7E7E7),
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        disabledIndicatorColor = Color.Transparent
//                    )
//                )
//                Spacer(Modifier.height(itemSpacing))
//            }
//
//            item {
//                HintInputField(
//                    value = state.wishlist.specialRequests,
//                    hint = "Special Requirements",
//                    onValueChange = {
//                        viewModel.onEvent(WishListNewUpdateEvent.EnteredSpecialRequests(it))
//                    },
//                    onFocusChange = {
//                        viewModel.onEvent(WishListNewUpdateEvent.ChangedSpecialRequestsFocus(it))
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
//                        viewModel.onEvent(WishListNewUpdateEvent.SaveWishList)
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
//                ) {
//                    Text("Submit Request")
//                }
//            }
//        }
//    }
//}
