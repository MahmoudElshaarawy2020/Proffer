package com.example.myapplication.presentation.change_password

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.request.ChangePasswordRequest
import com.example.myapplication.presentation.navigation.Screen
import com.example.myapplication.presentation.utils.CustomTextField
import com.example.myapplication.util.Result

@Composable
fun ChangePasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ChangePasswordViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit
) {

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var changePasswordRequest = ChangePasswordRequest(oldPassword, newPassword, confirmPassword)
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val token by dataStoreManager.getToken.collectAsState(initial = null)
    val changePasswordState by viewModel.changePasswordState.collectAsState()

    LaunchedEffect(changePasswordState) {
        when (changePasswordState) {
            is Result.Success -> {
                Log.d("TAG", "change password Success: ${changePasswordState.data}")

                onNavigateToHome()
            }

            is Result.Error -> {
                Toast.makeText(
                    context,
                    "change password failed: ${changePasswordState.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }

            is Result.Loading -> {
                Log.d("TAG", "changing password : ${changePasswordState.message}")
            }

            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(id = R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.light_white))
                .padding(start = 24.dp, top = 32.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow_img),
                    contentDescription = null
                )
            }

            Row {
                Text(
                    text = "English",
                    textDecoration = TextDecoration.Underline
                )

                Image(
                    painter = painterResource(id = R.drawable.earth_ic),
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
            text = "Change Password",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.orange),
            lineHeight = 28.sp
        )
        Text(
            text = "Personalize your experience",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.dark_grey),
            lineHeight = 25.sp
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
            text = "Old Password",
            fontSize = 17.sp,
            lineHeight = 21.sp,
            color = colorResource(id = R.color.dark_blue),
            fontWeight = FontWeight.Medium
        )

        CustomTextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            isPassword = true,
            isEncrypted = true,
            label = "old password",
            focusedBorderColor = colorResource(id = R.color.lighter_grey),
            unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
            cursorColor = colorResource(id = R.color.light_grey),
            isFocused = false
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
            text = "New Password",
            fontSize = 17.sp,
            lineHeight = 21.sp,
            color = colorResource(id = R.color.dark_blue),
            fontWeight = FontWeight.Medium
        )

        CustomTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = "new password",
            isEncrypted = true,
            isPassword = true,
            focusedBorderColor = colorResource(id = R.color.lighter_grey),
            unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
            cursorColor = colorResource(id = R.color.light_grey),
            isFocused = false
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
            text = "Confirm Password",
            fontSize = 17.sp,
            lineHeight = 21.sp,
            color = colorResource(id = R.color.dark_blue),
            fontWeight = FontWeight.Medium
        )

        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            isPassword = true,
            isEncrypted = true,
            label = "confirm password",
            focusedBorderColor = colorResource(id = R.color.lighter_grey),
            unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
            cursorColor = colorResource(id = R.color.light_grey),
            isFocused = false
        )

        Spacer(modifier = modifier.size(height = 8.dp, width = 0.dp))

        Spacer(modifier = modifier.size(height = 64.dp, width = 0.dp))
        Button(
            modifier = modifier
                .size(width = 230.dp, height = 50.dp),
            onClick = {
                token?.let { viewModel.changePassword(it, changePasswordRequest) }
                if (changePasswordState is Result.Success) {
                    navController.navigate(Screen.Login.route)
                    Toast.makeText(context, "change password success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "change password failed", Toast.LENGTH_SHORT).show()
                }
            },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
        ) {
            Text(
                text = "Save",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.light_white),
                lineHeight = 25.sp
            )

        }
        Spacer(modifier = modifier.size(height = 45.dp, width = 0.dp))


    }
}
