package com.example.myapplication.presentation.navigation.navbar_screens.bids

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.presentation.navigation.navbar_screens.more.faq.FAQItem
import com.example.myapplication.util.Result
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BidsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BidsViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val token by dataStoreManager.getToken.collectAsState(initial = null)

    val bidsState = viewModel.getBidsState.collectAsState()
    val isLoadingMore = viewModel.isLoadingMore.collectAsState()
    val scrollState = rememberLazyListState()


    //Need to be changed
    LaunchedEffect(key1 = true) {
        token?.let {
            viewModel.getBids(
                projectId = 0,
                rate = 0,
                minPrice = 0,
                maxPrice = 500000,
                token = it
            )
        }
    }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                val totalItemsCount = scrollState.layoutInfo.totalItemsCount

                if (lastVisibleItem != null && lastVisibleItem.index >= totalItemsCount - 1) {
                    token?.let {
                        viewModel.loadMoreBids(
                            projectId = 0,
                            rate = 0,
                            minPrice = 0,
                            maxPrice = 500000,
                            token = it
                        )
                    }
                }
            }
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
                    "Bids",
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


        when (val result = bidsState.value) {
            is Result.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Result.Success -> {
                val bidsList = result.data?.data ?: emptyList()
                if (bidsList.isEmpty()) {
                    Column(
                        modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.no_bids_img),
                            contentDescription = "No Bids",
                            modifier = Modifier.size(200.dp)
                        )

                        Text(
                            text = "No bids available",
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(bidsList) { bid ->
                            bid?.let { BidCard("Ahmed", "Apartment", "30000 L.E") }
                        }

                        item {
                            if (isLoadingMore.value) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }

            is Result.Error -> {
                var showMessage by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(5000)
                    showMessage = false
                }

                if (showMessage) {
                    val rawMessage = bidsState.value.message
                    val parsedMessage = rawMessage?.let {
                        Regex("\"message\"\\s*:\\s*\"([^\"]+)\"")
                            .find(it)
                            ?.groupValues?.get(1)
                    } ?: "Unable to load data"

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(Color.Red, shape = RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "$parsedMessage unable to load Bids. Please login!",
                            color = Color.White
                        )
                    }
                }


                Column(
                    modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.no_bids_img),
                        contentDescription = "No Bids",
                        modifier = Modifier.size(200.dp)
                    )

                    Text(
                        text = "No bids available",
                        color = Color.Black,
                        fontSize = 16.sp
                    )

                    Spacer(Modifier.height(150.dp))
                }
            }

            is Result.Idle -> TODO()
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Preview
@Composable
private fun BidsScreenPrev() {
    BidsScreen(modifier = Modifier, rememberNavController())
}
