package com.example.myapplication.presentation.navigation.navbar_screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.navigation.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val backgroundColor = colorResource(R.color.light_white)
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(color = backgroundColor, darkIcons = true)
    }
    Column(
        modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Screen")

    }
}