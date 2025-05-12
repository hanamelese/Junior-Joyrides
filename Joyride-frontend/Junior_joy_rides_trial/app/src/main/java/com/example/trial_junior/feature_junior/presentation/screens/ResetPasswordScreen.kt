//package com.example.trial_junior.feature_junior.presentation.screens
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBackIosNew
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material.icons.filled.LockReset
//import androidx.compose.material.icons.filled.MailOutline
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
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
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//
//@Composable
//fun ResetPasswordScreen(navController: NavHostController, viewModel: AuthViewModel = hiltViewModel()) {
//    var email by remember { mutableStateOf("") }
//    var resetCode by remember { mutableStateOf("") }
//    var newPassword by remember { mutableStateOf("") }
//    var confirmNewPassword by remember { mutableStateOf("") }
//    var isNewPasswordVisible by remember { mutableStateOf(false) }
//    var isConfirmNewPasswordVisible by remember { mutableStateOf(false) }
//
//    val resetPasswordResult by viewModel.resetPasswordResult.collectAsState(initial = null)
//    val isResettingPassword by viewModel.isResetPasswordLoadingReset.collectAsState()
//    val resetPasswordError by viewModel.resetPasswordError.collectAsState()
//
//    LaunchedEffect(resetPasswordResult) {
//        resetPasswordResult?.let { result ->
//            if (result.isSuccess) {
//                val authResponse = result.getOrNull()
//                println("Password reset successful: ${authResponse?.message}")
//                navController.navigate("login") {
//                    popUpTo("resetPassword") { inclusive = true }
//                }
//                viewModel.resetResetPasswordResult()
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        Icon(
//            imageVector = Icons.Filled.LockReset,
//            contentDescription = "Reset Password Icon",
//            modifier = Modifier.size(48.dp),
//            tint = Color(0xFFC5AE3D)
//        )
//        Text(
//            "Reset Your Password",
//            style = MaterialTheme.typography.headlineSmall,
//            color = Color(0xFFC5AE3D)
//        )
//        Divider(color = Color.Gray, thickness = 1.dp)
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email Address") },
//            leadingIcon = { Icon(Icons.Filled.MailOutline, contentDescription = "Email Icon") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp)
//        )
//
//        OutlinedTextField(
//            value = resetCode,
//            onValueChange = { resetCode = it },
//            label = { Text("Reset Code") },
//            leadingIcon = { Icon(Icons.Filled.LockReset, contentDescription = "Reset Code Icon") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp)
//        )
//
//        OutlinedTextField(
//            value = newPassword,
//            onValueChange = { newPassword = it },
//            label = { Text("New Password") },
//            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "New Password Icon") },
//            trailingIcon = {
//                val image = if (isNewPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                val description = if (isNewPasswordVisible) "Hide new password" else "Show new password"
//                IconButton(onClick = { isNewPasswordVisible = !isNewPasswordVisible }) {
//                    Icon(imageVector = image, contentDescription = description)
//                }
//            },
//            visualTransformation = if (isNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp)
//        )
//
//        OutlinedTextField(
//            value = confirmNewPassword,
//            onValueChange = { confirmNewPassword = it },
//            label = { Text("Confirm New Password") },
//            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm New Password Icon") },
//            trailingIcon = {
//                val image = if (isConfirmNewPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                val description = if (isConfirmNewPasswordVisible) "Hide confirm new password" else "Show confirm new password"
//                IconButton(onClick = { isConfirmNewPasswordVisible = !isConfirmNewPasswordVisible }) {
//                    Icon(imageVector = image, contentDescription = description)
//                }
//            },
//            visualTransformation = if (isConfirmNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp)
//        )
//
//        Button(
//            onClick = {
//                if (newPassword == confirmNewPassword && email.isNotBlank() && resetCode.isNotBlank() && newPassword.isNotBlank()) {
//                    viewModel.performResetPassword(email, resetCode, newPassword)
//                } else {
//                    println("Reset Password validation failed: Passwords do not match or fields are empty")
//                }
//            },
//            enabled = !isResettingPassword,
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D))
//        ) {
//            Text(if (isResettingPassword) "Resetting..." else "Reset Password", color = Color.White)
//            if (isResettingPassword) {
//                Spacer(modifier = Modifier.width(8.dp))
//                CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
//            }
//        }
//
//        if (resetPasswordError != null) {
//            Text(
//                text = resetPasswordError!!,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//        TextButton(onClick = { navController.popBackStack() }) {
//            Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back to Login", tint = Color(0xFFC5AE3D))
//            Spacer(modifier = Modifier.width(4.dp))
//            Text("Back to Login", color = Color(0xFFC5AE3D))
//        }
//    }
//}
//
