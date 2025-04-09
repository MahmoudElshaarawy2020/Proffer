package com.example.myapplication.presentation.splash

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(
    navController: NavController,
    dataStoreManager: DataStoreManager
) {
    LaunchedEffect(Unit) {
        delay(3000)

        val token = dataStoreManager.getToken.first()

        Log.d("SplashScreen", "Token = $token")

        if (token.isNullOrEmpty()) {
            navController.navigate(Screen.OnBoarding.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    var bottomVisible by remember { mutableStateOf(false) }
    var topVisible by remember { mutableStateOf(false) }

    val bottomOffsetY by animateDpAsState(
        targetValue = if (bottomVisible) 0.dp else 300.dp,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        label = "bottom offset animation"
    )

    val topOffsetY by animateDpAsState(
        targetValue = if (topVisible) 0.dp else (-300).dp,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        label = "top offset animation"
    )

    LaunchedEffect(Unit) {
        delay(300)
        topVisible = true
        bottomVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.light_pink))
    ) {
        Image(
            painter = painterResource(R.drawable.top_splash_img),
            contentDescription = "Top Sliding Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = topOffsetY)
                .height(300.dp)
                .align(Alignment.TopCenter)
        )

        Image(
            painter = painterResource(R.drawable.logo_img),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center)
        )

        Image(
            painter = painterResource(R.drawable.bottom_splash_img),
            contentDescription = "Bottom Sliding Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = bottomOffsetY)
                .height(300.dp)
                .align(Alignment.BottomCenter)
        )
    }
}
