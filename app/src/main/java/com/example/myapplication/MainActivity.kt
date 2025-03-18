package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.presentation.navigation.AppNavigation
import com.example.myapplication.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val systemUiController =
                com.google.accompanist.systemuicontroller.rememberSystemUiController()

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent, darkIcons = true
                )
                systemUiController.setNavigationBarColor(Color.White, true)
            }

            LaunchedEffect(Unit) {
                val token = dataStoreManager.getToken.first()
                navController.navigate(
                    if (token.isNullOrEmpty()) Screen.OnBoarding.route else Screen.Home.route
                ) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
            AppNavigation(
                navController = navController,
                startDestination = Screen.Splash.route,
                dataStoreManager = dataStoreManager
            )
        }
    }

}
