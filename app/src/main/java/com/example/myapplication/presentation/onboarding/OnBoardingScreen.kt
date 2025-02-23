package com.example.myapplication.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.navigation.Screen

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit = {},
    onRoleClickBoarding: (Int) -> Unit = {},
    navController: NavController) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        IdentityDialog(
            onDismiss = { showDialog = false },
            onRoleClick = { role ->
                onRoleClickBoarding(role)
            }
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier.size(width = 190.dp, height = 50.dp),
            painter = painterResource(id = R.drawable.logo_img),
            contentDescription = null
        )
        Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))

        Image(
            modifier = modifier.size(300.dp),
            painter = painterResource(id = R.drawable.onboarding_img),
            contentDescription = null
        )

        Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))

        Text(
            text = stringResource(id = R.string.Build_yor_house),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.orange),
            lineHeight = 28.sp
        )
        Spacer(modifier = modifier.size(height = 30.dp, width = 0.dp))
        Text(
            text = stringResource(id = R.string.personalize),
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.dark_grey),
            lineHeight = 25.sp
        )

        Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = modifier
                    .size(width = 160.dp, height = 50.dp),
                onClick = { onNavigateToLogin() },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.dark_blue))
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.light_white),
                    lineHeight = 25.sp)
            }

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                modifier = modifier
                    .size(width = 160.dp, height = 50.dp),
                onClick = { showDialog = true },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.light_white),
                    lineHeight = 25.sp)
            }
        }
    }
}

