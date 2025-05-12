//package com.example.trial_junior.feature_junior.presentation.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBackIosNew
//import androidx.compose.material.icons.filled.MailOutline
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import kotlinx.coroutines.delay
//import kotlin.text.isNotEmpty
//
//@Composable
//fun ForgotPasswordScreen(navController: NavHostController, viewModel: AuthViewModel = hiltViewModel()) {
//    var email by remember { mutableStateOf("") }
//    var resetCode by remember { mutableStateOf("") }
//    var showResetCode by remember { mutableStateOf(false) }
//
//    val forgotPasswordResult by viewModel.forgotPasswordResult.collectAsState(initial = null)
//    val isForgotPasswordLoading by viewModel.isForgotPasswordLoading.collectAsState()
//    val forgotPasswordError by viewModel.forgotPasswordError.collectAsState()
//
//
//    LaunchedEffect(forgotPasswordResult) {
//        forgotPasswordResult?.let { result ->
//            if (result.isSuccess) {
//                val authResponse = result.getOrNull()
//                val receivedResetCode = authResponse?.resetCode
//                println("Forgot password code sent successfully: ${authResponse?.message}, Code: $receivedResetCode")
//                if ((receivedResetCode != null) && receivedResetCode.isNotEmpty()) {
//                    resetCode = receivedResetCode
//                    showResetCode = true
//                    delay(7000)
//                    showResetCode = false
//                }
//                navController.navigate("resetPassword") {
//                    popUpTo("forgotPassword") { inclusive = true }
//                }
//                viewModel.resetForgotPasswordResult()
//            }
//        }
//    }
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(
//            text = "Forgot Password",
//            style = TextStyle(
//                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
//                fontWeight = FontWeight.Bold
//            ),
//            modifier = Modifier.align(Alignment.Start)
//                .padding(bottom = 5.dp),
//        )
//        Divider(
//            color = Color.Gray,
//            thickness = 1.dp,
//            modifier = Modifier.padding(vertical = 8.dp)
//                .padding(0.dp)
//        )
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email Address") },
//            leadingIcon = { Icon(Icons.Filled.MailOutline, contentDescription = "Email Icon") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Surface(
//            color = Color(0xFFC5AE3D),
//            shape = RoundedCornerShape(16.dp),
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Button(
//                onClick = {
//                    if (email.isNotBlank()) {
//                        viewModel.performForgotPassword(email)
//                    }
//                },
//                enabled = !viewModel.isForgotPasswordLoading.collectAsState().value,
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D)),
//                shape = RoundedCornerShape(16.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(if (viewModel.isForgotPasswordLoading.collectAsState().value) "Sending Code..." else "Send Reset Code", color = Color.White)
//                if (viewModel.isForgotPasswordLoading.collectAsState().value) {
//                    Spacer(modifier = Modifier.width(8.dp))
//                    CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//        TextButton(onClick = { navController.popBackStack() }) {
//            Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back to Login")
//            Spacer(modifier = Modifier.width(4.dp))
//            Text("Back to Login", color = Color(0xFFC5AE3D))
//        }
//
//        if (forgotPasswordError != null) {
//            Text(
//                text = forgotPasswordError!!,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//        if (showResetCode) {
//            Surface(
//                modifier = Modifier
//                    .padding(top = 16.dp)
//                    .background(MaterialTheme.colorScheme.secondaryContainer)
//                    .padding(8.dp),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(
//                    text = "Reset Code: $resetCode",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.onSecondaryContainer
//                )
//            }
//        }
//    }
//}
