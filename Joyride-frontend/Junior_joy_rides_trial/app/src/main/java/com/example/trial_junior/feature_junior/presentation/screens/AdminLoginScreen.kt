package com.example.trial_junior.feature_junior.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.trial_junior.feature_junior.presentation.util.Screen
import com.example.trial_junior.feature_junior.presentation.viewModels.UserUiEvent
import com.example.trial_junior.feature_junior.presentation.viewModels.UserViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AdminLoginScreen(navController: NavHostController, viewModel: UserViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()

    // Handle navigation on successful admin login
    LaunchedEffect(state.token, state.user?.role) {
        if (state.token != null && state.user?.role?.lowercase() == "admin") {
            println("Admin Login successful: Token=${state.token}")
            navController.navigate(Screen.AdminDashboardScreen.route){
                popUpTo("adminLogin") { inclusive = true }
            }
            viewModel.resetState() // Reset state after navigation
        }
    }

    // Listen for snackbar events from UserViewModel
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UserUiEvent.ShowSnackbar -> {
                    println("Admin Login error: ${event.message}")
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set background to white
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Admin Icon",
            modifier = Modifier.size(48.dp),
            tint = Color(0xFFC5AE3D)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Admin Login",
            style = TextStyle(
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.align(Alignment.Start)
                .padding(bottom = 5.dp),
        )
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
                .padding(0.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Admin Email") },
            leadingIcon = { Icon(Icons.Filled.MailOutline, contentDescription = "Admin Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .shadow(elevation = 4.dp),
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

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Admin Password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Admin Password") },
            trailingIcon = {
                val image = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (isPasswordVisible) "Toggle password visibility" else "Toggle password visibility"
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .shadow(elevation = 4.dp),
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

        Surface(
            color = Color(0xFFC5AE3D),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotEmpty()) {
                        viewModel.loginAdmin(email, password)
                    } else {
                        println("Admin Login validation failed")
                        viewModel.setError("Email and password cannot be empty")
                    }
                },
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (state.isLoading) "Logging in..." else "Admin Login",
                    color = Color.White
                )
                if (state.isLoading) {
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                }
            }
        }

        if (state.error != null) {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back to Login")
            Spacer(modifier = Modifier.width(4.dp))
            Text("Back to Login", color = Color(0xFFC5AE3D))
        }
    }
}