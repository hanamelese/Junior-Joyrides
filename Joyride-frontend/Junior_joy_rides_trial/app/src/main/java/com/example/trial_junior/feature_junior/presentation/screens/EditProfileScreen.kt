package com.example.trial_junior.feature_junior.presentation.screens

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.trial_junior.R
import com.example.trial_junior.feature_junior.presentation.components.AppBottomNavigation
import com.example.trial_junior.feature_junior.presentation.components.HalfScreenMenu
import com.example.trial_junior.feature_junior.presentation.util.Screen
import com.example.trial_junior.feature_junior.presentation.viewModels.UserUiEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.UserViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditProfileScreen(navController: NavHostController, viewModel: UserViewModel = hiltViewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var backgroundImageUri by remember { mutableStateOf<Uri?>(null) }
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var isMenuOpen by remember { mutableStateOf(false) }

    // Launcher for picking profile image
    val profileImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    // Launcher for picking background image
    val backgroundImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        backgroundImageUri = uri
    }

    // Launcher for requesting permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            profileImageLauncher.launch("image/*")
        } else {
            viewModel.setError("Permission denied to access gallery")
        }
    }

    // Launcher for requesting permission for background image
    val backgroundPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            backgroundImageLauncher.launch("image/*")
        } else {
            viewModel.setError("Permission denied to access gallery")
        }
    }

    // Pre-fill fields with user data when available
    LaunchedEffect(state.user) {
        state.user?.let {
            firstName = it.firstName ?: ""
            lastName = it.lastName ?: ""
            email = it.email ?: ""
            profileImageUri = try {
                it.profileImageUrl?.takeIf { url -> url.isNotEmpty() }?.let { Uri.parse(it) }
            } catch (e: Exception) {
                null // Fallback to null if parsing fails
            }
            backgroundImageUri = try {
                it.backgroundImageUrl?.takeIf { url -> url.isNotEmpty() }?.let { Uri.parse(it) }
            } catch (e: Exception) {
                null // Fallback to null if parsing fails
            }
        }
    }

    // Fetch profile data only if user is null (avoid redundant calls)
    LaunchedEffect(Unit) {
        if (state.user == null) {
            viewModel.getMyProfile()
        }
    }

    // Listen for navigation events from ViewModel
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                UserUiEvent.Back -> {
                    navController.navigate(Screen.ProfileScreen.route) {
                        popUpTo(Screen.EditProfileScreen.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
                UserUiEvent.SaveProfile -> {
                    navController.navigate(Screen.ProfileScreen.route) {
                        popUpTo(Screen.EditProfileScreen.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
                is UserUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
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
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        // Overlay background image if available
                        if (backgroundImageUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = backgroundImageUri,
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
                            painter = if (profileImageUri != null) {
                                rememberAsyncImagePainter(
                                    model = profileImageUri,
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
                        text = firstName.ifEmpty { "Full Name" },
                        fontSize = 26.sp,
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                    )
                    Text(
                        text = email.ifEmpty { "name@gmail.com" },
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 40.dp,
                                vertical = 8.dp
                            )
                            .shadow(elevation = 10.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F8F8))
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "First Name",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        TextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = { Text("Enter First Name") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFE5E4E4),
                                unfocusedContainerColor = Color(0xFFE5E4E4),
                                disabledContainerColor = Color(0xFFE5E4E4)
                            ),
                            singleLine = false,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 8.dp
                                )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Last Name",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        TextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = { Text("Enter Last Name") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFE5E4E4),
                                unfocusedContainerColor = Color(0xFFE5E4E4),
                                disabledContainerColor = Color(0xFFE5E4E4)
                            ),
                            singleLine = false,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 8.dp
                                )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Email",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Enter Email") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFE5E4E4),
                                unfocusedContainerColor = Color(0xFFE5E4E4),
                                disabledContainerColor = Color(0xFFE5E4E4)
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 8.dp
                                )
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Box {
                                Column(
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text("Profile ", fontSize = 20.sp)
                                    Text("Picture:", fontSize = 20.sp)
                                }
                            }

                            Box {
                                Column(
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text("Background", fontSize = 20.sp)
                                    Text("Picture:", fontSize = 20.sp)
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .background(Color(0xFFE5E4E4), shape = RoundedCornerShape(8.dp))
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp)
                                ) {
                                    if (profileImageUri != null) {
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                model = profileImageUri,
                                                placeholder = painterResource(id = R.drawable.icon),
                                                error = painterResource(id = R.drawable.icon)
                                            ),
                                            contentDescription = "Profile Image",
                                            modifier = Modifier.size(40.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.icon),
                                            contentDescription = "Default Profile Image",
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = {
                                            val permission = if (Build.VERSION.SDK_INT >= 33) {
                                                Manifest.permission.READ_MEDIA_IMAGES
                                            } else {
                                                Manifest.permission.READ_EXTERNAL_STORAGE
                                            }
                                            permissionLauncher.launch(permission)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDEDEDE)),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text("BROWSE", fontSize = 12.sp, color = Color.Black)
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .background(Color(0xFFE5E4E4), shape = RoundedCornerShape(8.dp))
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp)
                                ) {
                                    if (backgroundImageUri != null) {
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                model = backgroundImageUri,
                                                placeholder = painterResource(id = R.drawable.icon),
                                                error = painterResource(id = R.drawable.icon)
                                            ),
                                            contentDescription = "Background Image",
                                            modifier = Modifier.size(40.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.icon),
                                            contentDescription = "Default Background Image",
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = {
                                            val permission = if (Build.VERSION.SDK_INT >= 33) {
                                                Manifest.permission.READ_MEDIA_IMAGES
                                            } else {
                                                Manifest.permission.READ_EXTERNAL_STORAGE
                                            }
                                            backgroundPermissionLauncher.launch(permission)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDEDEDE)),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text("BROWSE", fontSize = 12.sp, color = Color.Black)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Button(
                                onClick = {
                                    if (email.isNotEmpty()) {
                                        viewModel.updateProfile(
                                            email,
                                            firstName,
                                            lastName,
                                            email,
                                            null,
                                            profileImageUri?.toString(),
                                            backgroundImageUri?.toString()
                                        )
                                    } else {
                                        viewModel.setError("Email cannot be empty")
                                    }
                                },
                                enabled = !state.isLoading,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4CAF50)
                                )
                            ) {
                                Text("Save", color = Color.White)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { viewModel.onBack() },
                                enabled = !state.isLoading,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF44336)
                                )
                            ) {
                                Text("Cancel", color = Color.White)
                            }
                        }
                    }
                }
            }

            // Loading Indicator
            if (state.isLoading) {
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
//import android.Manifest
//import android.net.Uri
//import android.os.Build
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
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
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import coil.compose.rememberAsyncImagePainter
//import com.example.trial_junior.R
//import com.example.trial_junior.feature_junior.presentation.components.HalfScreenMenu
//import com.example.trial_junior.feature_junior.presentation.util.Screen
//import com.example.trial_junior.feature_junior.presentation.viewModels.UserUiEvent
//import com.example.trial_junior.feature_junior.presentation.viewModels.UserViewModel
//import kotlinx.coroutines.flow.collectLatest
//
//@Composable
//fun EditProfileScreen(navController: NavHostController, viewModel: UserViewModel = hiltViewModel()) {
//    var firstName by remember { mutableStateOf("") }
//    var lastName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
//    var backgroundImageUri by remember { mutableStateOf<Uri?>(null) }
//    val state by viewModel.state.collectAsState()
//    val snackbarHostState = remember { SnackbarHostState() }
//    var isMenuOpen by remember { mutableStateOf(false) }
//
//    // Launcher for picking profile image
//    val profileImageLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        profileImageUri = uri
//    }
//
//    // Launcher for picking background image
//    val backgroundImageLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        backgroundImageUri = uri
//    }
//
//    // Launcher for requesting permission
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            // Permission granted, proceed with image picking
//            profileImageLauncher.launch("image/*")
//        } else {
//            viewModel.setError("Permission denied to access gallery")
//        }
//    }
//
//    // Launcher for requesting permission for background image
//    val backgroundPermissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            backgroundImageLauncher.launch("image/*")
//        } else {
//            viewModel.setError("Permission denied to access gallery")
//        }
//    }
//
//    // Pre-fill fields with user data when available
//    LaunchedEffect(state.user) {
//        state.user?.let {
//            firstName = it.firstName ?: ""
//            lastName = it.lastName ?: ""
//            email = it.email ?: ""
//        }
//    }
//
//    // Fetch profile data only if user is null (avoid redundant calls)
//    LaunchedEffect(Unit) {
//        if (state.user == null) {
//            viewModel.getMyProfile()
//        }
//    }
//
//    // Listen for navigation events from ViewModel
//    LaunchedEffect(Unit) {
//        viewModel.eventFlow.collectLatest { event ->
//            when (event) {
//                UserUiEvent.Back -> {
//                    navController.navigate(Screen.ProfileScreen.route) {
//                        popUpTo(Screen.EditProfileScreen.route) { inclusive = true }
//                        launchSingleTop = true
//                    }
//                }
//                UserUiEvent.SaveProfile -> {
//                    navController.navigate(Screen.ProfileScreen.route) {
//                        popUpTo(Screen.EditProfileScreen.route) { inclusive = true }
//                        launchSingleTop = true
//                    }
//                }
//                is UserUiEvent.ShowSnackbar -> {
//                    snackbarHostState.showSnackbar(message = event.message)
//                }
//            }
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(bottom = 56.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top
//        ) {
//            item {
//                if (state.isLoading) {
//                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
//                }
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(170.dp)
//                        .background(
//                            color = if (backgroundImageUri != null) Color.Transparent else Color.LightGray
//                        )
//                        .padding(bottom = 12.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    if (backgroundImageUri != null) {
//                        Image(
//                            painter = rememberAsyncImagePainter(backgroundImageUri),
//                            contentDescription = "Background Image",
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(170.dp),
//                            contentScale = ContentScale.Crop
//                        )
//                    }
//                    Image(
//                        painter = if (profileImageUri != null) {
//                            rememberAsyncImagePainter(profileImageUri)
//                        } else {
//                            painterResource(id = R.drawable.profile)
//                        },
//                        contentDescription = "Profile Image",
//                        modifier = Modifier
//                            .size(120.dp)
//                            .absoluteOffset(y = 95.dp)
//                            .clip(CircleShape),
//                        contentScale = ContentScale.Crop
//                    )
//                }
//                Spacer(modifier = Modifier.height(60.dp))
//                Text(
//                    text = firstName.ifEmpty { "Full Name" },
//                    fontSize = 26.sp,
//                    color = Color.Black,
//                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
//                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
//                )
//                Text(
//                    text = email.ifEmpty { "name@gmail.com" },
//                    fontSize = 20.sp,
//                    style = MaterialTheme.typography.headlineMedium,
//                    modifier = Modifier.padding(start = 16.dp)
//                )
//
//                Spacer(modifier = Modifier.height(40.dp))
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            horizontal = 40.dp,
//                            vertical = 8.dp
//                        )
//                        .shadow(elevation = 10.dp),
//                    shape = RoundedCornerShape(8.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F8F8))
//                ) {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(
//                        text = "First Name",
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    TextField(
//                        value = firstName,
//                        onValueChange = { firstName = it },
//                        label = { Text("Enter First Name") },
//                        colors = TextFieldDefaults.colors(
//                            focusedContainerColor = Color(0xFFE5E4E4),
//                            unfocusedContainerColor = Color(0xFFE5E4E4),
//                            disabledContainerColor = Color(0xFFE5E4E4)
//                        ),
//                        singleLine = false,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        shape = RoundedCornerShape(10.dp),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                horizontal = 10.dp,
//                                vertical = 8.dp
//                            )
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(
//                        text = "Last Name",
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    TextField(
//                        value = lastName,
//                        onValueChange = { lastName = it },
//                        label = { Text("Enter Last Name") },
//                        colors = TextFieldDefaults.colors(
//                            focusedContainerColor = Color(0xFFE5E4E4),
//                            unfocusedContainerColor = Color(0xFFE5E4E4),
//                            disabledContainerColor = Color(0xFFE5E4E4)
//                        ),
//                        singleLine = false,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        shape = RoundedCornerShape(10.dp),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                horizontal = 10.dp,
//                                vertical = 8.dp
//                            )
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(
//                        text = "Email",
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    TextField(
//                        value = email,
//                        onValueChange = { email = it },
//                        label = { Text("Enter Email") },
//                        colors = TextFieldDefaults.colors(
//                            focusedContainerColor = Color(0xFFE5E4E4),
//                            unfocusedContainerColor = Color(0xFFE5E4E4),
//                            disabledContainerColor = Color(0xFFE5E4E4)
//                        ),
//                        singleLine = true,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        shape = RoundedCornerShape(10.dp),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                horizontal = 10.dp,
//                                vertical = 8.dp
//                            )
//                    )
//
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                    ) {
//                        Box {
//                            Column(
//                                verticalArrangement = Arrangement.Top
//                            ) {
//                                Text("Profile ", fontSize = 20.sp)
//                                Text("Picture:", fontSize = 20.sp)
//                            }
//                        }
//
//                        Box {
//                            Column(
//                                verticalArrangement = Arrangement.Top
//                            ) {
//                                Text("Background", fontSize = 20.sp)
//                                Text("Picture:", fontSize = 20.sp)
//                            }
//                        }
//                    }
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(120.dp)
//                                .background(Color(0xFFE5E4E4), shape = RoundedCornerShape(8.dp))
//                        ) {
//                            Column(
//                                verticalArrangement = Arrangement.Center,
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(8.dp)
//                            ) {
//                                if (profileImageUri != null) {
//                                    Image(
//                                        painter = rememberAsyncImagePainter(profileImageUri),
//                                        contentDescription = "Profile Image",
//                                        modifier = Modifier.size(40.dp),
//                                        contentScale = ContentScale.Crop
//                                    )
//                                } else {
//                                    Image(
//                                        painter = painterResource(id = R.drawable.icon),
//                                        contentDescription = "Default Profile Image",
//                                        modifier = Modifier.size(40.dp)
//                                    )
//                                }
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Button(
//                                    onClick = {
//                                        val permission = if (Build.VERSION.SDK_INT >= 33) {
//                                            Manifest.permission.READ_MEDIA_IMAGES
//                                        } else {
//                                            Manifest.permission.READ_EXTERNAL_STORAGE
//                                        }
//                                        permissionLauncher.launch(permission)
//                                    },
//                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDEDEDE)),
//                                    modifier = Modifier.height(30.dp)
//                                ) {
//                                    Text("BROWSE", fontSize = 12.sp, color = Color.Black)
//                                }
//                            }
//                        }
//
//                        Box(
//                            modifier = Modifier
//                                .size(120.dp)
//                                .background(Color(0xFFE5E4E4), shape = RoundedCornerShape(8.dp))
//                        ) {
//                            Column(
//                                verticalArrangement = Arrangement.Center,
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(8.dp)
//                            ) {
//                                if (backgroundImageUri != null) {
//                                    Image(
//                                        painter = rememberAsyncImagePainter(backgroundImageUri),
//                                        contentDescription = "Background Image",
//                                        modifier = Modifier.size(40.dp),
//                                        contentScale = ContentScale.Crop
//                                    )
//                                } else {
//                                    Image(
//                                        painter = painterResource(id = R.drawable.icon),
//                                        contentDescription = "Default Background Image",
//                                        modifier = Modifier.size(40.dp)
//                                    )
//                                }
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Button(
//                                    onClick = {
//                                        val permission = if (Build.VERSION.SDK_INT >= 33) {
//                                            Manifest.permission.READ_MEDIA_IMAGES
//                                        } else {
//                                            Manifest.permission.READ_EXTERNAL_STORAGE
//                                        }
//                                        backgroundPermissionLauncher.launch(permission)
//                                    },
//                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDEDEDE)),
//                                    modifier = Modifier.height(30.dp)
//                                ) {
//                                    Text("BROWSE", fontSize = 12.sp, color = Color.Black)
//                                }
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        horizontalArrangement = Arrangement.Start
//                    ) {
//                        Button(
//                            onClick = {
//                                if (email.isNotEmpty()) {
//                                    viewModel.updateProfile(email, firstName, lastName, email, null)
//                                } else {
//                                    viewModel.setError("Email cannot be empty")
//                                }
//                            },
//                            enabled = !state.isLoading,
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color(0xFF4CAF50)
//                            )
//                        ) {
//                            Text("Save", color = Color.White)
//                        }
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Button(
//                            onClick = { viewModel.onBack() },
//                            enabled = !state.isLoading,
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color(0xFFF44336)
//                            )
//                        ) {
//                            Text("Cancel", color = Color.White)
//                        }
//                    }
//                }
//            }
//        }
//
//        // Loading Indicator
//        if (state.isLoading) {
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


