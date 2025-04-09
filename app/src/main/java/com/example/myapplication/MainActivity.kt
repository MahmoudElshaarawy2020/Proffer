package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
//      installSplashScreen()
        setContent {
            val navController = rememberNavController()
            val systemUiController =
                com.google.accompanist.systemuicontroller.rememberSystemUiController()

            var startDestination by remember { mutableStateOf<String?>(null) }

            // SideEffect for system UI colors
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent, darkIcons = true
                )
                systemUiController.setNavigationBarColor(Color.White, false)
            }

            LaunchedEffect(Unit) {
                val token = dataStoreManager.getToken.first()
                startDestination = Screen.Splash.route // Always start with Splash
            }

            // Only show navigation when ready
            startDestination?.let {
                AppNavigation(
                    navController = navController,
                    startDestination = it,
                    dataStoreManager = dataStoreManager
                )
            }
        }

    }

}
