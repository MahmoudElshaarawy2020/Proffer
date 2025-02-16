package com.example.myapplication.presentation.navigation.navbar_screens.bids

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun BidsScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bids Screen")
    }
}