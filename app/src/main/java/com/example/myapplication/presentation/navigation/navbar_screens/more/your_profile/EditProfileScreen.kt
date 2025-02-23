package com.example.myapplication.presentation.navigation.navbar_screens.more.your_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.request.EditProfileRequest
import com.example.myapplication.presentation.utils.CustomTextField
import com.example.myapplication.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: YourProfileViewModel = hiltViewModel(),
) {
    var userName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var editProfileRequest = EditProfileRequest(userName, phoneNumber, address)
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val token by dataStoreManager.getToken.collectAsState(initial = null)
    val yourProfileState by viewModel.yourProfileState.collectAsState()
    val imageUrl = (yourProfileState as? Result.Success)?.data?.userData?.profileImage ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            title = {
                Text(
                    "Edit Profile",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 35.dp),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(R.color.light_white)),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(R.drawable.back_arrow_img),
                        contentDescription = "Back",
                        tint = Color.Unspecified
                    )
                }
            },
        )

        Box(
            modifier
                .size(100.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                modifier = modifier.size(70.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.client_img)
                    .error(R.drawable.client_img)
                    .build(),
                contentDescription = "Profile Image",
            )

            Row(modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End) {
                Icon(
                    painter = painterResource(R.drawable.edit_ic),
                    contentDescription = "Edit",
                    modifier = Modifier
                        .size(23.dp),
                    tint = colorResource(R.color.dark_blue)
                )
            }


        }

        Column (modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center){
            Text(
                "User Name",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                )

            CustomTextField(
                value = userName,
                onValueChange = { userName = it },
                label = "Your Name",
                focusedBorderColor = colorResource(id = R.color.lighter_grey),
                unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                cursorColor = colorResource(id = R.color.light_grey),
                isFocused = false
            )
        }

        Column (modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center){
            Text(
                "Phone Number",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
            )

            CustomTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Your Phone Number",
                focusedBorderColor = colorResource(id = R.color.lighter_grey),
                unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                cursorColor = colorResource(id = R.color.light_grey),
                isFocused = false,
                isPhoneNumber = true
            )
        }

        Column (modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center){
            Text(
                "Address",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
            )

            CustomTextField(
                value = address,
                onValueChange = { address = it },
                label = "Your address",
                focusedBorderColor = colorResource(id = R.color.lighter_grey),
                unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                cursorColor = colorResource(id = R.color.light_grey),
                isFocused = false
            )
        }

        Spacer(
            modifier = Modifier
                .size(height = 100.dp, width = 0.dp)
        )


        Button(
            modifier = modifier
                .size(width = 230.dp, height = 50.dp),
            onClick = {
                token?.let { viewModel.editYourProfile(it, editProfileRequest) }
            },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
        ) {
            Text(
                text = "Save changes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.light_white),
                lineHeight = 25.sp)
        }

    }


}