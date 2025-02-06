package com.example.myapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.presentation.log_in.LoginScreen
import com.example.myapplication.ui.presentation.onboarding.OnBoardingScreen
import com.example.myapplication.ui.presentation.sign_up.SignUpScreen


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val paddingValues = remember { PaddingValues() }

    NavHost(
        navController = navController,
        startDestination = Screen.OnBoarding.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)

    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                modifier = modifier,
                navController = navController,
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) }
            )
        }
        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(
                modifier = modifier,
                navController = navController,
                onIdentityClick = { navController.navigate(Screen.SignUp.route) },
               onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                modifier = modifier,
                navController = navController,
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }
    }
}