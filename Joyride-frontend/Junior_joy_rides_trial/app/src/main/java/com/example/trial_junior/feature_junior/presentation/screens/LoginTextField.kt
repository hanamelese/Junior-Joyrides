package com.example.trial_junior.feature_junior.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    value: String,
    labelText: String,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    readOnly: Boolean
){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(imageVector = leadingIcon, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(percent = 30),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFE7E7E7), // Light grey
            unfocusedContainerColor = Color(0xFFE7E7E7),
            disabledContainerColor = Color(0xFFE7E7E7),
            focusedIndicatorColor = Color.Transparent, // Removes outline when focused
            unfocusedIndicatorColor = Color.Transparent, // Removes outline when unfocused
            disabledIndicatorColor = Color.Transparent // Removes outline when disabled
        )
    )

}
@Preview(showBackground = true)
@Composable
fun prevTextField(){
    LoginTextField(
        value = "",
        labelText = "password",
        onValueChange={},
        readOnly = true
    )

}