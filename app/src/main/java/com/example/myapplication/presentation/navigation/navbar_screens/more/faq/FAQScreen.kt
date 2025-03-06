package com.example.myapplication.presentation.navigation.navbar_screens.more.faq

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.example.myapplication.util.Result
import com.example.myapplication.data.response.DataItem
import com.example.myapplication.presentation.navigation.navbar_screens.more.MoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MoreViewModel = hiltViewModel()
) {
    val faqState = viewModel.getFaqState.collectAsState()
    val isLoadingMore = viewModel.isLoadingMore.collectAsState()
    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        viewModel.getFAQ()
    }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                val totalItemsCount = scrollState.layoutInfo.totalItemsCount

                if (lastVisibleItem != null && lastVisibleItem.index >= totalItemsCount - 1) {
                    viewModel.loadMoreFAQ()
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
                    "FAQs",
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

        Text(
            text = "Get The Respond You Need",
            fontSize = 28.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        when (val result = faqState.value) {
            is Result.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Result.Success -> {
                val faqList = result.data?.data ?: emptyList()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(faqList) { faq ->
                        faq?.let { FAQItem(it) }
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

            is Result.Error -> {
                Text(
                    text = "Failed to load FAQs: ${result.message}",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

            is Result.Idle -> TODO()
        }
    }
}

@Composable
fun FAQItem(faq: DataItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.question_mark_ic),
                contentDescription = "Question Icon",
                tint = colorResource(R.color.orange),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = faq.question ?: "Unknown Question",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = faq.answer ?: "No answer available",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
private fun FAQScreenPrev() {
    FAQScreen(Modifier, rememberNavController())
}