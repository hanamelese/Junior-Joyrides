package com.example.trial_junior.feature_junior.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.trial_junior.feature_junior.presentation.util.Screen
import com.example.trial_junior.feature_junior.presentation.viewModels.UserViewModel

@Composable
fun SignupScreen(navController: NavHostController, viewModel: UserViewModel = hiltViewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var isPolicyChecked by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()

    // Navigate on successful signup
    LaunchedEffect(state.user) {
        if (state.user != null) {
            navController.navigate(Screen.LandingScreen.route) {
                popUpTo(Screen.SignupScreen.route) { inclusive = true }
            }
            viewModel.resetState() // Reset state after navigation
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
            .padding(bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Sign up",
            style = TextStyle(
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 5.dp),
        )
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(0.dp)
        )

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        if (state.error != null) {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First name", color = Color.Black, fontSize = 16.sp) },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "First name") },
            shape = RoundedCornerShape(percent = 30),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp),
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
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name", color = Color.Black, fontSize = 16.sp) },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Last name") },
            shape = RoundedCornerShape(percent = 30),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp),
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
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.Black, fontSize = 16.sp) },
            leadingIcon = { Icon(Icons.Filled.MailOutline, contentDescription = "Email") },
            shape = RoundedCornerShape(percent = 30),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp),
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
            label = { Text("Password", color = Color.Black, fontSize = 16.sp) },
            shape = RoundedCornerShape(percent = 30),
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
            trailingIcon = {
                val image = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (isPasswordVisible) "Hide password" else "Show password"
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp),
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
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm password", color = Color.Black, fontSize = 16.sp) },
            shape = RoundedCornerShape(percent = 30),
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm password") },
            trailingIcon = {
                val image = if (isConfirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (isConfirmPasswordVisible) "Hide confirm password" else "Show confirm password"
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE7E7E7),
                unfocusedContainerColor = Color(0xFFE7E7E7),
                disabledContainerColor = Color(0xFFE7E7E7),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isPolicyChecked,
                onCheckedChange = { isPolicyChecked = it }
            )
            Text("I agree with the ")
            Text(
                text = "policy",
                color = Color(0xFFC5AE3D),
                modifier = Modifier.clickable{
                    navController.navigate(Screen.PrivacyAndPolicyScreen.route)
                }
            )
            Text(" and ")
            Text(
                text = "privacy",
                color = Color(0xFFC5AE3D),
                modifier = Modifier.clickable{
                    navController.navigate(Screen.PrivacyAndPolicyScreen.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Surface(
            color = Color(0xFFC5AE3D),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (password == confirmPassword && isPolicyChecked && firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotEmpty()) {
                        viewModel.registerUser(firstName, lastName, email, password)
                    } else {
                        viewModel.setError("Please fill all fields, ensure passwords match, and agree to the policy")
                    }
                },
                enabled = !state.isLoading && isPolicyChecked && password.isNotEmpty() && password == confirmPassword && email.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5AE3D)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (state.isLoading) "Signing up..." else "Signup",
                    color = Color.White
                )
                if (state.isLoading) {
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account? ")
            TextButton(onClick = { navController.navigate(Screen.LoginScreen.route) }) {
                Text(
                    text = "Signin",
                    color = Color(0xFFC5AE3D),
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }
}



