package com.example.myapplication.ui.presentation.log_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }
}