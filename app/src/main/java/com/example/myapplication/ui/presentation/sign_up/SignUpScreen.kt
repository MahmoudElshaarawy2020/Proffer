package com.example.myapplication.ui.presentation.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.presentation.utils.CustomTextField

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    onSignUpClick: () -> Unit = {}) {

    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LazyColumn(
            modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    modifier = modifier
                        .size(width = 190.dp, height = 50.dp)
                        .padding(bottom = 8.dp),
                    painter = painterResource(id = R.drawable.logo_img),
                    contentDescription = null
                )

                Text(
                    text = "Create Your Account",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.orange),
                    lineHeight = 28.sp
                )
                Text(
                    text = "Get exclusive offers",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.dark_grey),
                    lineHeight = 25.sp
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp,top = 17.dp),
                    text = "User Name",
                    fontSize = 17.sp,
                    lineHeight = 21.sp,
                    color = colorResource(id = R.color.dark_blue),
                    fontWeight = FontWeight.Medium
                )

                CustomTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = "Your Name",
                    focusedBorderColor = colorResource(id = R.color.light_grey),
                    unfocusedBorderColor = colorResource(id = R.color.light_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp,top = 17.dp),
                    text = "Email",
                    fontSize = 17.sp,
                    lineHeight = 21.sp,
                    color = colorResource(id = R.color.dark_blue),
                    fontWeight = FontWeight.Medium
                )

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Your Email",
                    isEmail = true,
                    focusedBorderColor = colorResource(id = R.color.light_grey),
                    unfocusedBorderColor = colorResource(id = R.color.light_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp,top = 17.dp),
                    text = "Phone Number",
                    fontSize = 17.sp,
                    lineHeight = 21.sp,
                    color = colorResource(id = R.color.dark_blue),
                    fontWeight = FontWeight.Medium
                )

                CustomTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    isPhoneNumber = true,
                    label = "Your Phone Number",
                    focusedBorderColor = colorResource(id = R.color.light_grey),
                    unfocusedBorderColor = colorResource(id = R.color.light_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp,top = 17.dp),
                    text = "Address",
                    fontSize = 17.sp,
                    lineHeight = 21.sp,
                    color = colorResource(id = R.color.dark_blue),
                    fontWeight = FontWeight.Medium
                )

                CustomTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = "Your Address",
                    focusedBorderColor = colorResource(id = R.color.light_grey),
                    unfocusedBorderColor = colorResource(id = R.color.light_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp,top = 17.dp),
                    text = "Password",
                    fontSize = 17.sp,
                    lineHeight = 21.sp,
                    color = colorResource(id = R.color.dark_blue),
                    fontWeight = FontWeight.Medium
                )

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    isPassword = true,
                    isEncrypted = true,
                    label = "Your Password",
                    focusedBorderColor = colorResource(id = R.color.light_grey),
                    unfocusedBorderColor = colorResource(id = R.color.light_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp,top = 17.dp),
                    text = "Confirm Password",
                    fontSize = 17.sp,
                    lineHeight = 21.sp,
                    color = colorResource(id = R.color.dark_blue),
                    fontWeight = FontWeight.Medium
                )

                CustomTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    isEncrypted = true,
                    label = "Confirm Password",
                    focusedBorderColor = colorResource(id = R.color.light_grey),
                    unfocusedBorderColor = colorResource(id = R.color.light_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {

            }
        }




    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    SignUpScreen()
}