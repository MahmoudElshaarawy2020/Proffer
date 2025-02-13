package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.presentation.navigation.AppNavigation
import com.example.myapplication.presentation.navigation.Screen
import com.example.myapplication.presentation.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
            var startDestination by remember { mutableStateOf<String?>(null) }

            // Check token asynchronously
            LaunchedEffect(Unit) {
                val token = dataStoreManager.getToken.first()
                startDestination =
                    if (token.isNullOrEmpty()) Screen.Login.route else Screen.Home.route
            }

            if (startDestination == null) {
                SplashScreen()
            } else {
                AppNavigation(
                    navController = navController, startDestination = startDestination!!
                )
            }
        }
    }
}
