package com.example.myapplication.presentation.navigation.navbar_screens.more

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.presentation.navigation.Screen
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

    LaunchedEffect(token) {
        if (!token.isNullOrEmpty()) {
            Log.d("Using Token", token!!)
            viewModel.getMoreAboutUser(token!!)
        }
    }

    val profileState by viewModel.profileState.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = colorResource(R.color.dark_blue))
                    .size(width = 270.dp, height = 100.dp)
            ) {

                when (profileState) {
                    is Result.Loading ->
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Loading...",
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    is Result.Success -> {
                        val profile = (profileState as Result.Success).data?.data
                        if (profile != null) {
                            Column(
                                modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {
                                Text(
                                    text = profile.name ?: "No Name",
                                    modifier
                                        .fillMaxWidth()
                                        .padding(start = 50.dp),
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Start)
                                Text(
                                    text = profile.email?: "No Email",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    textAlign = TextAlign.Start)
                            }
                        } else {
                            Text("Error: No profile data", color = Color.Red)
                        }
                    }
                    is Result.Error -> Text("Error: ${(profileState as Result.Error).message}", color = Color.Red)
                }
            }
            Row(modifier.fillMaxWidth()) {
                Spacer(modifier.size(40.dp))

                val imageUrl = (profileState as? Result.Success)?.data?.data?.profileImage ?: ""
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.client_img)
                        .error(R.drawable.client_img)
                        .build(),
                    contentDescription = "Profile Image",
                    modifier = modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(3.dp, color = colorResource(R.color.orange), CircleShape)
                )
            }
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
        CustomRow(text = "Settings", icon = R.drawable.settings_ic)
        Spacer(modifier.size(10.dp))
        CustomRow(text = "Contact Us", icon = R.drawable.phone_ic)
        Spacer(modifier.size(10.dp))
        CustomRow(text = "FAQ", icon = R.drawable.faq_ic)
        Spacer(modifier.size(10.dp))
        CustomRow(text = "About Us", icon = R.drawable.right_ic)
        Spacer(modifier.size(10.dp))
        CustomRow(text = "Terms & Conditions", icon = R.drawable.terms_ic)
        Spacer(modifier.size(10.dp))
        CustomRow(text = "Privacy Policy", icon = R.drawable.privacy)
        Spacer(modifier.size(10.dp))
        CustomRow(text = "Log Out", icon = R.drawable.logout_ic)
        Spacer(modifier.size(10.dp))
    }
}

