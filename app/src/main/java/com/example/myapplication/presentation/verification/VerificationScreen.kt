package com.example.myapplication.presentation.verification

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.request.VerificationRequest
import com.example.myapplication.util.Result


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    email: String,
    viewModel: VerificationViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier,
    otpLength: Int = 4,
    onNavigateToLogin : () -> Unit
) {

    var otp by remember { mutableStateOf(List(otpLength) { "" }) }
    val focusRequesters = List(otpLength) { FocusRequester() }
    var isOtpComplete by remember { mutableStateOf(false) }
    var context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.light_white))
                .padding(start = 24.dp, top = 64.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow_img),
                    contentDescription = null
                )
            }

        }
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 16.dp, end = 24.dp),
            thickness = 1.dp
        )
        Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))

        Image(
            modifier = modifier
                .size(width = 190.dp, height = 50.dp)
                .padding(bottom = 8.dp),
            painter = painterResource(id = R.drawable.logo_img),
            contentDescription = null
        )

        Text(
            text = "Verify Your Email",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.orange),
            lineHeight = 28.sp
        )
        Text(
            text = "Please Enter The Received \n" +
                    "OTP Code",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.dark_grey),
            lineHeight = 25.sp
        )
        Spacer(modifier = modifier.size(height = 120.dp, width = 0.dp))
        Text(
            text = "Enter The 4 OTP Number",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            otp.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.length <= 1) {
                            otp = otp.toMutableList().apply { set(index, newValue) }
                            if (newValue.isNotEmpty() && index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }

                        isOtpComplete = otp.joinToString("").length == otpLength
                    },
                    modifier = Modifier
                        .width(50.dp)
                        .focusRequester(focusRequesters[index])
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Backspace && value.isEmpty() && index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                            false
                        },
                    textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }
        }

        Text(
            text = "00:28",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Spacer(modifier = modifier.size(height = 200.dp, width = 0.dp))

        Button(
            modifier = modifier
                .size(width = 230.dp, height = 50.dp),
            onClick = {
                if (isOtpComplete) {
                    val request = VerificationRequest(email = email, code = otp.joinToString(""))
                    viewModel.verification(request)
                    if (viewModel.verificationState.value is Result.Success) {
                        onNavigateToLogin()
                    }else {
                        Toast.makeText(context, "Verification failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            enabled = isOtpComplete,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
        ) {
            Text(
                text = "Verify",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.light_white),
                lineHeight = 25.sp
            )
        }

        Text(
            modifier = modifier.clickable {},
            text = "Resend code",
            textDecoration = TextDecoration.Underline,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.orange),
        )

        Spacer(modifier = modifier.size(height = 64.dp, width = 0.dp))
    }
}