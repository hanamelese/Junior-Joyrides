package com.example.trial_junior.feature_junior.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HintInputField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    textColor: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    leadingIcon: ImageVector? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(elevation = 4.dp),
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) },
            placeholder = {
                Text(
                    text = hint,
                    color = Color.Black,
                    fontSize = fontSize
                )
            },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(imageVector = leadingIcon, contentDescription = null)
                }
            },
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = TextStyle(
                color = textColor,
                fontSize = fontSize
            ),
            shape = RoundedCornerShape(percent = 30),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE7E7E7),
                unfocusedContainerColor = Color(0xFFE7E7E7),
                disabledContainerColor = Color(0xFFE7E7E7),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            visualTransformation = visualTransformation
        )
    }
}

@Preview(showBackground = true, name = "Age Field")
@Composable
fun JuniorInputFieldPreviewAge() {
    HintInputField(
        value = "10",
        hint = "Age",
        keyboardType = KeyboardType.Number,
        onValueChange = {},
        onFocusChange = {}
    )
}