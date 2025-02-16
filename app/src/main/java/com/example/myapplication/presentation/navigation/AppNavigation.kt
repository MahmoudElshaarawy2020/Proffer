package com.example.myapplication.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.R
import com.example.myapplication.presentation.navigation.navbar_screens.home.HomeScreen
import com.example.myapplication.presentation.log_in.LoginScreen
import com.example.myapplication.presentation.navigation.navbar_screens.bids.BidsScreen
import com.example.myapplication.presentation.navigation.navbar_screens.bottom_navbar.BottomNavigationBar
import com.example.myapplication.presentation.navigation.navbar_screens.more.MoreScreen
import com.example.myapplication.presentation.navigation.navbar_screens.navbar_items.BottomNavItem
import com.example.myapplication.presentation.navigation.navbar_screens.projects.ProjectsScreen
import com.example.myapplication.presentation.new_password.NewPasswordScreen
import com.example.myapplication.presentation.onboarding.OnBoardingScreen
import com.example.myapplication.presentation.register.SignUpScreen
import com.example.myapplication.presentation.splash.SplashScreen
import com.example.myapplication.presentation.verification.VerificationScreen


@Composable
fun AppNavigation(modifier: Modifier = Modifier,startDestination: String, navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val bottomNavItems = listOf(
        BottomNavItem("Home", R.drawable.home_ic),
        BottomNavItem("Projects", R.drawable.project_ic),
        BottomNavItem("Bids", R.drawable.bids_ic),
        BottomNavItem("More", R.drawable.more_ic)
    )
    val selectedItemIndex =
        bottomNavItems.indexOfFirst { it.label.lowercase() in (currentRoute ?: "") }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute != Screen.Login.route && currentRoute != Screen.SignUp.route) {
                BottomNavigationBar(
                    items = bottomNavItems,
                    selectedItem = if (selectedItemIndex != -1) selectedItemIndex else 0,
                    onItemSelected = { index ->
                        val route = when (index) {
                            0 -> Screen.Home.route
                            1 -> Screen.Projects.route
                            2 -> Screen.Bids.route
                            3 -> Screen.More.route
                            else -> Screen.Home.route
                        }
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
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
                    onNavigateToVerification = {
                        navController.navigate(
                            Screen.Verification.createRoute(
                                email = it
                            )
                        )
                    }
                )
            }


            composable(
                route = "verification_screen/{email}",
                arguments = listOf(navArgument("email") { type = NavType.StringType })
            ) { backStackEntry ->
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

            composable(Screen.More.route) {
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

            composable(Screen.Projects.route) {
                ProjectsScreen(
                    modifier = modifier,
                    navController = navController
                )
            }

            composable(Screen.Bids.route) {
                BidsScreen(
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
}