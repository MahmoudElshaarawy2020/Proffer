package com.example.myapplication.presentation.navigation.navbar_screens.more

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.presentation.navigation.Screen
import com.example.myapplication.presentation.navigation.navbar_screens.more.log_out.LogoutDialog
import com.example.myapplication.util.Result


@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MoreViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val token by dataStoreManager.getToken.collectAsState(initial = null)
    val profileState by viewModel.profileState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        LogoutDialog(
            onDismiss = { showDialog = false },
            onYesClick = {
                showDialog = false
                token?.let {
                    Log.d("Using Token", it)
                    viewModel.logout(it)
                    navController.navigate(Screen.OnBoarding.route)
                }
            },
            onNoClick = { showDialog = false }
        )
    }



    LaunchedEffect(token) {
        if (!token.isNullOrEmpty()) {
            Log.d("Using Token", token!!)
            viewModel.getMoreAboutUser(token!!)
        }
    }


    Column(
        modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent, shape = RoundedCornerShape(12.dp))
        ) {
            val imageRef = createRef()
            val boxRef = createRef()



            Box(
                modifier = Modifier
                    .padding(start = 60.dp, end = 30.dp)
                    .height(80.dp)
                    .background(
                        color = colorResource(R.color.dark_blue),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .constrainAs(boxRef) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    },
                contentAlignment = Alignment.Center

            ) {
                Column(
                    modifier
                        .fillMaxWidth()
                        .padding(start = 35.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {

                    when (profileState) {
                        is Result.Loading ->
                            Text(
                                text = "Loading...",
                                color = Color.White,
                                fontSize = 20.sp
                            )

                        is Result.Success -> {
                            val profile = (profileState as Result.Success).data?.userData
                            if (profile != null) {
                                Text(
                                    text = profile.name ?: "No Name",
                                    modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp),
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    textAlign = TextAlign.Start
                                )
                                Text(
                                    text = profile.email ?: "No Email",
                                    modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                        .alpha(0.7f),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Start
                                )

                            } else {
                                Text("Error: No profile data", color = Color.Red)
                            }
                        }

                        is Result.Error -> Text(
                            "Error: ${(profileState as Result.Error).message}",
                            color = Color.Red
                        )

                        is Result.Idle -> TODO()
                    }
                }

            }
            val imageUrl = (profileState as? Result.Success)?.data?.userData?.profileImage ?: ""
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.client_img)
                    .error(R.drawable.client_img)
                    .build(),
                contentDescription = "User Image",
                modifier = Modifier
                    .padding(start = 40.dp)
                    .clip(CircleShape)
                    .constrainAs(imageRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(50.dp)
                    .border(2.dp, color = colorResource(R.color.orange), shape = CircleShape),
                contentScale = ContentScale.Crop
            )

        }

        Divider(
            modifier
                .size(width = 300.dp, height = 1.dp)
                .padding(top = 20.dp, bottom = 20.dp),
            color = colorResource(R.color.lighter_grey)
        )

        Spacer(
            modifier
                .size(40.dp)
        )

        CustomRow(
            text = "Your Profile",
            icon = R.drawable.person_ic,
            onItemClick = {
                navController.navigate(Screen.YourProfile.route)
            })
        Spacer(modifier.size(10.dp))
        CustomRow(text = "Settings", icon = R.drawable.settings_ic,
            onItemClick = {
                navController.navigate(Screen.Settings.route)
            }
        )
        Spacer(modifier.size(10.dp))
        CustomRow(text = "Contact Us", icon = R.drawable.phone_ic,
            onItemClick = {
                navController.navigate(Screen.ContactUs.route)
            })
        Spacer(modifier.size(10.dp))
        CustomRow(
            text = "FAQ",
            icon = R.drawable.faq_ic,
            onItemClick = { navController.navigate(Screen.FAQ.route) })
        Spacer(modifier.size(10.dp))
        CustomRow(text = "About Us", icon = R.drawable.right_ic, onItemClick = {
            navController.navigate(Screen.AboutUs.route)
        })
        Spacer(modifier.size(10.dp))
        CustomRow(text = "Terms & Conditions", icon = R.drawable.terms_ic, onItemClick = {
            navController.navigate(Screen.Terms.route)
        })
        Spacer(modifier.size(10.dp))
        CustomRow(text = "Privacy Policy", icon = R.drawable.privacy, onItemClick = {
            navController.navigate(Screen.Privacy.route)
        })
        Spacer(modifier.size(10.dp))
        CustomRow(
            text = "Log Out",
            icon = R.drawable.logout_ic,
            onItemClick = {
                showDialog = true
            }
        )
        Spacer(modifier.size(10.dp))
    }
}

