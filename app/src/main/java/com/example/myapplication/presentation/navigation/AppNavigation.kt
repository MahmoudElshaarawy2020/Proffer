package com.example.myapplication.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.home.HomeScreen
import com.example.myapplication.presentation.log_in.LoginScreen
import com.example.myapplication.presentation.navbar_screens.more.MoreScreen
import com.example.myapplication.presentation.new_password.NewPasswordScreen
import com.example.myapplication.presentation.onboarding.OnBoardingScreen
import com.example.myapplication.presentation.register.SignUpScreen
import com.example.myapplication.presentation.splash.SplashScreen
import com.example.myapplication.presentation.verification.VerificationScreen


@Composable
fun AppNavigation(modifier: Modifier = Modifier, startDestination: String, navController: NavHostController) {

    val paddingValues = remember { PaddingValues() }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)

    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                modifier = modifier,
                navController = navController,
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                onNavigateToHome = { navController.navigate(Screen.Home.route) }
            )
        }
        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(
                modifier = modifier,
                navController = navController,
                onRoleClickBoarding = { role ->
                    navController.navigate(Screen.SignUp.createRoute(role))
                },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },

                )
        }

        composable(
            route = "signUp_screen/{role}",
            arguments = listOf(navArgument("role") { type = NavType.IntType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getInt("role") ?: 1

            SignUpScreen(
                modifier = modifier,
                navController = navController,
                role = role,
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToVerification = { navController.navigate(Screen.Verification.createRoute(email = it)) }
            )
        }


        composable(
            route = "verification_screen/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) {backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerificationScreen(
                modifier = modifier,
                email = email,
                navController = navController,
                onNavigateToLogin = { navController.navigate(Screen.NewPassword.route) }
            )
        }
        composable(Screen.NewPassword.route) {
            NewPasswordScreen(
                modifier = modifier,
            )
        }

        composable(Screen.MoreScreen.route) {
            MoreScreen(
                modifier = modifier,
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                modifier = modifier,
                navController = navController
            )
        }

        composable(Screen.Splash.route) {
            SplashScreen(
                modifier = modifier,
            )
        }
    }
}