package com.example.myapplication.presentation.navigation.navbar_screens.more.your_profile

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.presentation.navigation.navbar_screens.more.MoreViewModel
import com.example.myapplication.util.Result
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun YourProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: MoreViewModel = hiltViewModel(),
) {
    val backgroundColor = colorResource(R.color.dark_blue)
    val systemUiController = rememberSystemUiController()
    val profileState by viewModel.profileState.collectAsState()
    val imageUrl = (profileState as? Result.Success)?.data?.data?.profileImage ?: ""
    val email = (profileState as? Result.Success)?.data?.data?.email ?: "example@gmail.com"
    val phoneNumber = (profileState as? Result.Success)?.data?.data?.phone ?: "0000000000"
    val location = (profileState as? Result.Success)?.data?.data?.address ?: "Unknown Location"

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(color = backgroundColor, darkIcons = false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.light_white))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Your Profile",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
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
                modifier = Modifier.fillMaxSize()
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
                text = "User Name Here",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.dark_blue)
            )

            Spacer(modifier = Modifier.height(10.dp))


            Button(
                onClick = {  },
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange))
            ) {
                Text(text = "Edit", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(80.dp))
            ProfileInfoItem(label = "Email Address", value = email, isEditable = true)
            ProfileInfoItem(label = "Phone Number", value = phoneNumber)
            ProfileInfoItem(label = "Location Address", value = location)

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { /* Handle Delete Account */ },
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
        Text(text = value,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            fontSize = 14.sp, color = Color.Gray)
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}