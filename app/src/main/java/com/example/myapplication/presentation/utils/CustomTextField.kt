package com.example.myapplication.presentation.utils

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = colorResource(id = R.color.light_grey),
    label: String = "",
    focusedBorderColor: Color,
    unfocusedBorderColor: Color,
    cursorColor: Color,
    isFocused: Boolean,
    backgroundColor: Color = colorResource(id = R.color.lighter_white),
    maxLines: Int = 1,
    isPassword: Boolean = false,
    isEncrypted: Boolean = false,
    isError: Boolean = false,
    isPhoneNumber: Boolean = false,
    isEmail: Boolean = false,
    width: Int = 340,
    height: Int = 60,
    errorMessage: String = ""
) {

    var isFieldFocused by remember { mutableStateOf(isFocused) }

    val phoneNumberPattern = Regex("^[0-9]{11,15}$")
    val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[@#\$%^&+=!]).{8,}$")

    val actualErrorMessage = when {
        isPhoneNumber && value.isNotEmpty() && !value.matches(phoneNumberPattern) ->
            "Enter a valid phone number (11-15 digits)"
        isEmail && (value.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(value).matches()) ->
            "Enter a valid email address"
        isPassword && value.isNotEmpty() && !value.matches(passwordPattern) ->
            "Password must be 8+ chars, \n include 1 uppercase & 1 special char"
        isError -> errorMessage
        else -> ""
    }

    val hasError = actualErrorMessage.isNotEmpty()

    Column {
        OutlinedTextField(
            modifier = modifier
                .size(width = width.dp, height = height.dp)
                .onFocusChanged { focusState -> isFieldFocused = focusState.isFocused },
            value = value,
            onValueChange = onValueChange,
            maxLines = maxLines,
            textStyle = textStyle.copy(
                color = textColor
            ),
            visualTransformation = if (!isEncrypted) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (hasError) Color.Red else focusedBorderColor,
                unfocusedBorderColor = if (hasError) Color.Red else unfocusedBorderColor,
                cursorColor = cursorColor,
                containerColor = backgroundColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPhoneNumber) KeyboardType.Number else KeyboardType.Text
            ),

            isError = hasError,
            placeholder = {
                Text(
                    text = label,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.light_grey),
                    fontSize = 12.sp,
                    lineHeight = 21.sp)
            }
        )

        if (hasError) {
            Text(
                text = actualErrorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
