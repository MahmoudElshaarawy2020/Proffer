package com.example.myapplication.presentation.navigation.navbar_screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import com.example.myapplication.util.Result
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.response.HomeResponse
import com.example.myapplication.data.response.HomeSliderResponse


@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onContractorClick: (Int) -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val token by dataStoreManager.getToken.collectAsState(initial = null)


    val sliderState by homeViewModel.getSliderState.collectAsState()
    val contractorsState by homeViewModel.getContractorsState.collectAsState()
    val isLoading = sliderState is Result.Loading || contractorsState is Result.Loading

    LaunchedEffect(Unit) {
        homeViewModel.getSliders()
        token?.let { homeViewModel.getContractors(it) }
    }



    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white)),
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorResource(R.color.orange)
            )
        } else {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
                    .background(color = colorResource(R.color.light_white)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = modifier.padding(vertical = 16.dp)) {
                        Text(
                            text = "Welcome,",
                            color = colorResource(R.color.light_grey),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "Apps Square!",
                            color = Color.Black,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = {
                        onNotificationClick()
                    }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.alarm_ic),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }


                when (sliderState) {
                    is Result.Loading -> {
//                    CircularProgressIndicator(
//                        modifier = Modifier
//                            .size(48.dp)
//                            .align(Alignment.CenterHorizontally)
//                    )
                    }

                    is Result.Error -> {
                        Text(text = "Error loading images", color = Color.Red)
                    }

                    is Result.Success -> {
                        val sliderImages =
                            (sliderState as Result.Success<HomeSliderResponse>).data?.data
                        ImageProductPager(sliderImages)
                    }

                    is Result.Idle -> TODO()
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Top Contractors",
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                LazyRow(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    when (contractorsState) {
                        is Result.Loading -> {
                            item {
//                            CircularProgressIndicator(
//                                modifier = Modifier
//                                    .size(48.dp)
//                                    .padding(16.dp)
//                                    .align(Alignment.CenterHorizontally),
//                                color = Color.Gray
//                            )
                            }
                        }

                        is Result.Success -> {
                            val contractors =
                                (contractorsState as Result.Success<HomeResponse>).data?.data?.contractors

                            items(contractors?.size!!) { index ->
                                val contractor = contractors[index]
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable { contractor?.id?.let { onContractorClick(it) } }
                                ) {
                                    SubcomposeAsyncImage(
                                        model = contractor?.image?.takeIf { it.isNotBlank() }
                                            ?: R.drawable.client_img,
                                        contentDescription = "Contractor Image",
                                        loading = {
                                            CircularProgressIndicator(
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .align(Alignment.Center)
                                            )
                                        },
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                            .border(2.dp, Color.Black, CircleShape)
                                    )
                                    contractor?.name?.let {
                                        Text(
                                            text = it,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            tint = colorResource(R.color.yellow),
                                        )
                                        Text(
                                            text = contractor?.rateAvg.toString(),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }

                        is Result.Error -> {
                            item {
                                (contractorsState as Result.Error).message?.let {
                                    Text(
                                        text = it,
                                        color = Color.Red,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }

                        is Result.Idle -> TODO()
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Pending Projects",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Image(
                    painter = painterResource(id = R.drawable.sample_project_img),
                    contentDescription = "Pending Project",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .size(340.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}










