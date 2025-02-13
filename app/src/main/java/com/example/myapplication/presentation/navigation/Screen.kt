package com.example.myapplication.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object OnBoarding : Screen("onBoarding_screen")
    object Verification : Screen("verification_screen"){
        fun createRoute(email: String) = "verification_screen/$email"
    }
    object NewPassword : Screen("new_password_screen")
    object Home : Screen("home_screen")
    object Splash : Screen("splash_screen")

    object SignUp : Screen("signUp_screen/{role}") {
        fun createRoute(role: Int) = "signUp_screen/$role"
    }
}
