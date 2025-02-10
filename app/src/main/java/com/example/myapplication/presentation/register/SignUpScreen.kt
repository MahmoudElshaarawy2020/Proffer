package com.example.myapplication.presentation.register

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.presentation.utils.CustomTextField

@Composable
fun SignUpScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    onNavigateToLogin: () -> Unit = {}
) {

    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val request = RegisterRequest("A\'laa", "ssffdgds@gmail.com", "sfag","sfgd","dfdgfd","sdgsdfsdf","fadgfdf")
    viewModel.registerUser(request)
    val registerState by viewModel.registerState.collectAsState()
    Log.d("TAG", "SignUpScreen: ${registerState}")



    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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


        LazyColumn(
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
                        .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
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
                    focusedBorderColor = colorResource(id = R.color.lighter_grey),
                    unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
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
                    focusedBorderColor = colorResource(id = R.color.lighter_grey),
                    unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
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
                    focusedBorderColor = colorResource(id = R.color.lighter_grey),
                    unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
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
                    focusedBorderColor = colorResource(id = R.color.lighter_grey),
                    unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
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
                    focusedBorderColor = colorResource(id = R.color.lighter_grey),
                    unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }

            item {
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
                    isEncrypted = true,
                    label = "Confirm Password",
                    focusedBorderColor = colorResource(id = R.color.lighter_grey),
                    unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                    cursorColor = colorResource(id = R.color.light_grey),
                    isFocused = false
                )
            }


            item {
                Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))
                Button(
                    modifier = modifier
                        .size(width = 230.dp, height = 50.dp),
                    onClick = { },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.light_white),
                        lineHeight = 25.sp
                    )

                }
                Spacer(modifier = modifier.size(height = 8.dp, width = 0.dp))
                Text(
                    modifier = modifier.clickable {
                        onNavigateToLogin()
                    },
                    text = "Proceed to login",
                    textDecoration = TextDecoration.Underline,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.orange),
                    )

                Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))

            }
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    SignUpScreen()
}