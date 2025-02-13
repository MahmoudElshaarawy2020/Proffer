package com.example.myapplication.presentation.log_in

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.request.LoginRequest
import com.example.myapplication.presentation.utils.CustomTextField
import com.example.myapplication.util.Result

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    onNavigateToPassword: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current


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
                .padding(start = 24.dp,top = 64.dp , end = 24.dp),
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
            text = "Unlock Your Benefits",
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
            isEmail = true,
            label = "Your Email",
            focusedBorderColor = colorResource(id = R.color.lighter_grey),
            unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
            cursorColor = colorResource(id = R.color.light_grey),
            isFocused = false
        )

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
            label = "Your Password",
            isEncrypted = true,
            focusedBorderColor = colorResource(id = R.color.lighter_grey),
            unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
            cursorColor = colorResource(id = R.color.light_grey),
            isFocused = false
        )

        Spacer(modifier = modifier.size(height = 8.dp, width = 0.dp))
        Text(
            modifier = modifier.clickable {
                onNavigateToPassword()
            },
            text = "Can’t Remember Your Password",
            textDecoration = TextDecoration.Underline,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.orange),
        )

        Spacer(modifier = modifier.size(height = 200.dp, width = 0.dp))
        Button(
            modifier = modifier
                .size(width = 230.dp, height = 50.dp),
            onClick = {
                val request = LoginRequest(email = email, password = password)
                viewModel.login(request)
            },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
        ) {
            Text(
                text = stringResource(id = R.string.login),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.light_white),
                lineHeight = 25.sp
            )

        }

        Spacer(modifier = modifier.size(height = 16.dp, width = 0.dp))
        Text(
            modifier = modifier.clickable {
                onNavigateToSignUp()
            },
            text = "Want To Create Your Account",
            textDecoration = TextDecoration.Underline,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.orange),
        )
        Spacer(modifier = modifier.size(height = 45.dp, width = 0.dp))

        LaunchedEffect(loginState) {
            when (loginState) {
                is Result.Success -> {
                    Log.d("TAG", "login Success: ${loginState.data}")

                    onNavigateToPassword()
                }

                is Result.Error -> {
                    Toast.makeText(
                        context,
                        "login failed: ${loginState.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is Result.Loading -> {
                    Log.d("TAG", "login user: ${loginState.message}")
                }

                else -> Unit
            }
        }

    }
}