package com.example.myapplication.presentation.navigation.navbar_screens.more.about_us

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.navigation.navbar_screens.more.MoreViewModel
import com.example.myapplication.presentation.navigation.navbar_screens.more.faq.FAQItem
import com.example.myapplication.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MoreViewModel = hiltViewModel()
) {

    val aboutUsState = viewModel.getAboutUsState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getAboutUs()
    }

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
                    "About Us",
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

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier.fillMaxSize().verticalScroll(rememberScrollState())) {


            when (val result = aboutUsState.value) {
                is Result.Loading -> {
                    Text("Loading about us content...", fontSize = 18.sp, color = Color.Gray)
                }

                is Result.Success -> {
                    val aboutUsContent = aboutUsState.value.data?.data?.aboutUs ?: "No content available"
                    Text(
                        text = aboutUsContent ,
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is Result.Error -> {
                    Text(
                        text = "Failed to load about us: ${result.message}",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }

                is Result.Idle -> TODO()
            }

        }

    }
}