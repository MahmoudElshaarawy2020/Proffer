package com.example.myapplication.presentation.navigation.navbar_screens.home.contractor_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.util.Result
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContractorProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    contractorId: Int = 0,
    viewModel: ContractorProfileViewModel = hiltViewModel()
) {

    val sampleReviews =
        List(4) { "More Details About The Decorative Name Type You Would Like To Add To This Room." }
    val workImages = listOf(
        R.drawable.project_img,
        R.drawable.project_img,
        R.drawable.project_img,
        R.drawable.project_img,
        R.drawable.project_img,
    )

    val contractorProfileState by viewModel.getContractorProfileState.collectAsState()
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val token by dataStoreManager.getToken.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        token?.let { viewModel.getContractorProfile(it, contractorId) }
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
                    "Contractor Profile",
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

        when (contractorProfileState) {
            is Result.Loading -> {
                Text(text = "Loading...", modifier = Modifier.padding(16.dp))
            }

            is Result.Error -> {
                Text(text = "Error: ${(contractorProfileState as Result.Error).message}", modifier = Modifier.padding(16.dp), color = Color.Red)
            }

            is Result.Success -> {
                val contractor = (contractorProfileState as Result.Success).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFFFDF5EC))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = contractor?.data?.image ?: R.drawable.contractor_img,
                                contentDescription = "Contractor Image",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, colorResource(R.color.orange), CircleShape),
                                contentScale = ContentScale.Crop,

                                )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                contractor?.data?.name?.let {
                                    Text(
                                        text = it,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = colorResource(R.color.yellow),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = contractor?.data?.rateAvg.toString(), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Contractor BIO",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        contractor?.data?.bio?.let {
                            Text(
                                text = it,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    item {
                        Text(
                            text = "Memories of my works",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp),
                            fontSize = 16.sp
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            val images = contractor?.data?.workImages?.takeIf { it.isNotEmpty() } ?: listOf(
                                R.drawable.project_img,
                                R.drawable.project_img,
                                R.drawable.project_img,
                                R.drawable.project_img)

                            items(images) { imageUrl ->
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Work Image",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop,

                                )
                            }
                        }

                    }

                    item {
                        Text(
                            text = "My Client Reviews",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    items(contractor?.data?.reviews.orEmpty().filterNotNull()) { review ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            AsyncImage(
                                model = review.clientImage ?: R.drawable.client_img,
                                contentDescription = "User Image",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, colorResource(R.color.orange), CircleShape)
                                )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                review.clientName?.let { Text(text = it, fontWeight = FontWeight.Bold, fontSize = 14.sp) }
                                review.description?.let { Text(text = it, fontSize = 12.sp, color = Color.Gray) }
                                review.createdAt?.let { Text(text = it, fontSize = 10.sp, color = Color.Gray) }
                            }
                        }
                    }
                }
            }
        }
        }

    }

@Preview
@Composable
private fun ContractorProfilePrev() {
    ContractorProfileScreen(modifier = Modifier,rememberNavController())
}
