package com.example.myapplication.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object OnBoarding : Screen("onBoarding_screen")
    object Verification : Screen("verification_screen")
    object NewPassword : Screen("new_password_screen")

    object SignUp : Screen("signUp_screen/{role}") {
        fun createRoute(role: Int) = "signUp_screen/$role"
    }
}
