package com.example.myapplication.presentation.navigation

import NotificationScreen
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.presentation.change_password.ChangePasswordScreen
import com.example.myapplication.presentation.navigation.navbar_screens.home.HomeScreen
import com.example.myapplication.presentation.log_in.LoginScreen
import com.example.myapplication.presentation.navigation.navbar_screens.bids.BidsScreen
import com.example.myapplication.presentation.navigation.navbar_screens.bottom_navbar.BottomNavigationBar
import com.example.myapplication.presentation.navigation.navbar_screens.home.contractor_profile.ContractorProfileScreen
import com.example.myapplication.presentation.navigation.navbar_screens.more.MoreScreen
import com.example.myapplication.presentation.navigation.navbar_screens.more.about_us.AboutUsScreen
import com.example.myapplication.presentation.navigation.navbar_screens.more.contact_us.ContactUsScreen
import com.example.myapplication.presentation.navigation.navbar_screens.more.faq.FAQScreen
import com.example.myapplication.presentation.navigation.navbar_screens.more.privacy_policy.PrivacyScreen
import com.example.myapplication.presentation.navigation.navbar_screens.more.settings.SettingsScreen
import com.example.myapplication.presentation.navigation.navbar_screens.more.terms_conditions.TermsConditions
import com.example.myapplication.presentation.navigation.navbar_screens.more.your_profile.EditProfileScreen
import com.example.myapplication.presentation.navigation.navbar_screens.more.your_profile.YourProfileScreen
import com.example.myapplication.presentation.navigation.navbar_screens.navbar_items.BottomNavItem
import com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project.AddProjectScreen
import com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project.add_room.RoomDetailsScreen
import com.example.myapplication.presentation.navigation.navbar_screens.projects.project_home.ProjectsScreen
import com.example.myapplication.presentation.new_password.NewPasswordScreen
import com.example.myapplication.presentation.onboarding.OnBoardingScreen
import com.example.myapplication.presentation.register.SignUpScreen
import com.example.myapplication.presentation.splash.SplashScreen
import com.example.myapplication.presentation.verification.VerificationScreen


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Splash.route,
    navController: NavHostController,
    dataStoreManager: DataStoreManager
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Log.d("NavigationDebug", "Current Route: $currentRoute")
    val bottomNavItems = listOf(
        BottomNavItem("Home", R.drawable.home_ic),
        BottomNavItem("Projects", R.drawable.project_ic),
        BottomNavItem("Bids", R.drawable.bids_ic),
        BottomNavItem("More", R.drawable.more_ic)
    )
    val selectedItemIndex = when {
        currentRoute?.startsWith(Screen.Home.route) == true -> 0
        currentRoute?.startsWith(Screen.Projects.route) == true -> 1
        currentRoute?.startsWith(Screen.Bids.route) == true -> 2
        currentRoute?.startsWith(Screen.More.route) == true ||
                currentRoute == Screen.YourProfile.route -> 3

        else -> 0
    }


    Scaffold(
        modifier = Modifier
            .padding(bottom = 24.dp)
            .fillMaxSize()
            .background(Color.Transparent)
            .windowInsetsPadding(WindowInsets.systemBars),
        containerColor = colorResource(R.color.light_white)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.light_white))
                .padding(paddingValues),
            contentAlignment = Alignment.BottomCenter
        ) {
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
                        onNavigateToHome = { navController.navigate(Screen.Home.route) }
                    )
                }
                composable(Screen.NewPassword.route) {
                    NewPasswordScreen(
                        modifier = modifier,
                    )
                }


                composable(Screen.Notification.route) {
                    NotificationScreen(
                        modifier = modifier,
                        navController = navController
                    )
                }

                composable(Screen.More.route) {
                    MoreScreen(
                        navController = navController,
                        modifier = modifier,
                    )
                }

                composable(Screen.Home.route) {
                    HomeScreen(
                        modifier = modifier,
                        navController = navController,
                        onNotificationClick = {
                            navController.navigate(Screen.Notification.route)
                        },
                        onContractorClick = {
                            navController.navigate(Screen.ContractorProfile.createRoute(it))
                        }
                    )
                }

                composable(Screen.Projects.route) {
                    ProjectsScreen(
                        modifier = modifier,
                        navController = navController,
                        onTypeClickProject = {
                            navController.navigate(Screen.AddProject.route)
                        }
                    )
                }

                composable(Screen.Bids.route) {
                    BidsScreen(
                        modifier = modifier,
                        navController = navController
                    )
                }

                composable(Screen.AddProject.route) {
                    AddProjectScreen(
                        navController = navController
                    )
                }

                composable(Screen.Splash.route) {
                    SplashScreen(
                        navController = navController,
                        dataStoreManager = dataStoreManager
                    )
                }
                composable(Screen.EditProfile.route) {
                    EditProfileScreen(
                        navController = navController,
                    )
                }

                composable(Screen.ContactUs.route) {
                    ContactUsScreen(
                        navController = navController,
                    )
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(
                        navController = navController,
                        modifier = modifier,
                    )
                }



                composable(
                    route = Screen.ContractorProfile.route,
                    arguments = listOf(navArgument("contractorId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val contractorId = backStackEntry.arguments?.getInt("contractorId") ?: 0

                    ContractorProfileScreen(
                        navController = navController,
                        contractorId = contractorId
                    )
                }

                composable(Screen.Privacy.route) {
                    PrivacyScreen(
                        navController = navController,
                        modifier = modifier,
                    )
                }

                composable(Screen.AboutUs.route) {
                    AboutUsScreen(
                        navController = navController,
                        modifier = modifier,
                    )
                }

                composable(Screen.Terms.route) {
                    TermsConditions(
                        navController = navController,
                        modifier = modifier,
                    )
                }

                composable(Screen.RoomDetails.route) {
                    RoomDetailsScreen(
                        navController = navController,
                        onMaterialSelected = {}
                    )
                }

                composable(Screen.FAQ.route) {
                    FAQScreen(
                        navController = navController,
                        modifier = modifier,
                    )
                }


                composable(Screen.ChangePassword.route) {
                    ChangePasswordScreen(
                        navController = navController,
                        onNavigateToLogin = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }



                composable(Screen.YourProfile.route) {
                    YourProfileScreen(
                        modifier = modifier,
                        navController = navController,
                        onNavigateToOnboarding = {
                            navController.navigate(Screen.OnBoarding.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateToEditProfile = {
                            navController.navigate(Screen.EditProfile.route)
                        }
                    )
                }
            }

            if (
                currentRoute?.startsWith(Screen.Verification.route) != true &&
                currentRoute != Screen.Login.route &&
                currentRoute != Screen.SignUp.route &&
                currentRoute != Screen.OnBoarding.route &&
                currentRoute != Screen.NewPassword.route &&
                currentRoute != Screen.Splash.route &&
                currentRoute != Screen.AddProject.route &&
                currentRoute != Screen.EditProfile.route &&
                currentRoute != Screen.Settings.route &&
                currentRoute != Screen.Privacy.route &&
                currentRoute != Screen.AboutUs.route &&
                currentRoute != Screen.FAQ.route &&
                currentRoute != Screen.ChangePassword.route &&
                currentRoute != Screen.YourProfile.route &&
                currentRoute != Screen.Notification.route &&
                currentRoute != Screen.Terms.route &&
                currentRoute != Screen.ContactUs.route &&
                currentRoute != Screen.ContractorProfile.route &&
                currentRoute != Screen.RoomDetails.route

            ) {
                BottomNavigationBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
                        .background(Color.Transparent),
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
    }
}