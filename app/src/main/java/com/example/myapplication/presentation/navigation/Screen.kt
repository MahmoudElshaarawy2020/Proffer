package com.example.myapplication.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object OnBoarding : Screen("onBoarding_screen")
    object Verification : Screen("verification_screen"){
        fun createRoute(email: String) = "verification_screen/$email"
    }
    object NewPassword : Screen("new_password_screen")
    object Home : Screen("home_screen")
    object Projects : Screen("projects_screen")
    object AddProject : Screen("add_project_screen")
    object Bids : Screen("bids_screen")
    object Splash : Screen("splash_screen")
    object More : Screen("more_screen")
    object YourProfile : Screen("your_profile_screen")
    object EditProfile : Screen("edit_profile_screen")
    object ChangePassword : Screen("change_password_screen")
    object FAQ : Screen("faq_screen")
    object Settings : Screen("settings_screen")
    object AboutUs : Screen("about_us_screen")
    object Privacy : Screen("privacy_screen")
    object Terms : Screen("terms_screen")
    object Notification : Screen("notification_screen")
    object ContactUs : Screen("contact_us_screen")
    object RoomDetails : Screen("room_details_screen")

    object ContractorProfile : Screen("contractor_profile_screen/{contractorId}") {
        fun createRoute(contractorId: Int) = "contractor_profile_screen/$contractorId"
    }
    object SignUp : Screen("signUp_screen/{role}") {
        fun createRoute(role: Int) = "signUp_screen/$role"
    }
}
