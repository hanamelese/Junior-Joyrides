package com.example.trial_junior.feature_junior.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.trial_junior.feature_junior.presentation.util.Screen
import com.example.trial_junior.feature_junior.presentation.viewModels.UserState
import com.example.trial_junior.feature_junior.presentation.viewModels.UserViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: UserViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val userState by viewModel.state.collectAsState()

    // Navigate on successful login
    LaunchedEffect(userState.token) {
        if (userState.token != null) {
            onLoginSuccess(navController)
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Login",
            style = TextStyle(
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 5.dp),
        )
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(0.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("Enter your email", color = Color.Black, fontSize = 16.sp) },
            leadingIcon = { Icon(Icons.Filled.MailOutline, contentDescription = "Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            shape = RoundedCornerShape(30),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE7E7E7),
                unfocusedContainerColor = Color(0xFFE7E7E7),
                disabledContainerColor = Color(0xFFE7E7E7),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().shadow(elevation = 4.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Enter your password", color = Color.Black, fontSize = 16.sp) },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
            trailingIcon = {
                val image = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (isPasswordVisible) "Toggle password visibility" else "Toggle password visibility"
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            shape = RoundedCornerShape(30),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE7E7E7),
                unfocusedContainerColor = Color(0xFFE7E7E7),
                disabledContainerColor = Color(0xFFE7E7E7),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().shadow(elevation = 4.dp)
        )

        // Error Display Below Password Field
        if (userState.error != null) {
            Text(
                text = userState.error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = false, onCheckedChange = {})
            Text("Remember me")
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { navController.navigate("forgotPassword") }) {
                Text("Forgot password", color = Color(0xFFC5AE3D))
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = {
                navController.navigate(Screen.AdminLoginScreen.route) {
                    popUpTo("login") { inclusive = true }
                }
            }) {
                Text("Admin Login", color = Color(0xFFC5AE3D))
            }
        }
        Surface(
            color = Color(0xFFC5AE3D),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotEmpty()) {
                        viewModel.loginUser(email, password)
                    } else {
                        // Use setError method to update state properly
                        viewModel.setError("Email and password cannot be empty")
                    }
                },
                enabled = !userState.isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (userState.isLoading) "Logging in..." else "Login",
                        color = Color.White
                    )
                    if (userState.isLoading) {
                        Spacer(modifier = Modifier.width(8.dp))
                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                    }
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(
                "Contact us",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFC5AE3D)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { /* TODO: Handle YouTube click */ }) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "YouTube")
                }
                IconButton(onClick = { /* TODO: Handle Instagram click */ }) {
                    Icon(Icons.Filled.CameraAlt, contentDescription = "Instagram")
                }
                IconButton(onClick = { /* TODO: Handle Phone click */ }) {
                    Icon(Icons.Filled.Call, contentDescription = "Phone")
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Don't have an account? ")
            TextButton(onClick = {
                navController.navigate(Screen.SignupScreen.route) {
                    popUpTo("login") { inclusive = true }
                }
            }) {
                Text(
                    "Signup",
                    color = Color(0xFFC5AE3D)
                )
            }
        }
    }
}

private fun onLoginSuccess(navController: NavHostController) {
    navController.navigate(Screen.LandingScreen.route) {
        popUpTo("login") { inclusive = true }
    }
}

