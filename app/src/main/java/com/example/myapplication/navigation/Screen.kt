package com.example.myapplication.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object SignUp : Screen("signUp_screen")
    object OnBoarding : Screen("onBoarding_screen")
}