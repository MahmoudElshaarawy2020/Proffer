package com.example.myapplication.presentation.navigation.navbar_screens.more.your_profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.util.Result
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: YourProfileViewModel = hiltViewModel(),
    onNavigateToOnboarding: () -> Unit,
    navController: NavController,
    onNavigateToEditProfile: () -> Unit
) {
    val backgroundColor = colorResource(R.color.dark_blue)
    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val token by dataStoreManager.getToken.collectAsState(initial = null)
    val yourProfileState by viewModel.yourProfileState.collectAsState()
    val deleteAccountState by viewModel.deleteAccountState.collectAsState()
    val name = (yourProfileState as? Result.Success)?.data?.userData?.name ?: "Unknown"
    val imageUrl = (yourProfileState as? Result.Success)?.data?.userData?.profileImage ?: ""
    val email = (yourProfileState as? Result.Success)?.data?.userData?.email ?: "example@gmail.com"
    val phoneNumber = (yourProfileState as? Result.Success)?.data?.userData?.phone ?: "0000000000"
    val location =
        (yourProfileState as? Result.Success)?.data?.userData?.address ?: "Unknown Location"

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(color = backgroundColor, darkIcons = false)
    }

    LaunchedEffect(token) {
        if (!token.isNullOrEmpty()) {
            Log.d("Using Token", token!!)
            viewModel.getYourProfileData(token!!)
        }
    }

    LaunchedEffect(deleteAccountState) {
        when (deleteAccountState) {
            is Result.Success -> {
                Toast.makeText(context, "Account deleted successfully!", Toast.LENGTH_LONG).show()
                delay(1000)
                onNavigateToOnboarding()
                dataStoreManager.clearToken()
            }

            is Result.Error -> {
                val errorMessage = (deleteAccountState as Result.Error).message
                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.light_white))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Your Profile",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 45.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.white_arrow_back_ic),
                            contentDescription = "Back",
                            tint = Color.Unspecified
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
            )

            Spacer(modifier = Modifier.height(40.dp))

        }


        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp)
                .size(110.dp)
                .clip(CircleShape)
                .border(3.dp, colorResource(R.color.orange), CircleShape)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.client_img)
                    .error(R.drawable.client_img)
                    .build(),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp)
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))



            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.dark_blue)
            )


            Spacer(modifier = Modifier.height(10.dp))


            Button(
                onClick = {
                    onNavigateToEditProfile()
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange))
            ) {
                Text(text = "Edit", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))
            ProfileInfoItem(label = "Email Address", value = email, isEditable = true)
            ProfileInfoItem(label = "Phone Number", value = phoneNumber)
            ProfileInfoItem(label = "Location Address", value = location)

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    viewModel.deleteAccount(token!!)
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.dark_blue))
            ) {
                Text(text = "Delete Account", color = Color.White, fontSize = 16.sp)
            }

        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String, isEditable: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            if (isEditable) {
                Text(
                    text = "Edit",
                    modifier = Modifier
                        .clickable { }
                        .padding(end = 10.dp),
                    style = LocalTextStyle.current.copy(
                        color = colorResource(R.color.orange),
                        fontSize = 14.sp
                    )
                )
            }
        }
        Text(
            text = value,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            fontSize = 14.sp, color = Color.Gray
        )
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}