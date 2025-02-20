package com.example.myapplication.presentation.navigation.navbar_screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ImageProductPager(product: Product) {
    val items = product.images
    val pagerState = rememberPagerState(pageCount = { items.size })
    val coroutineScope = rememberCoroutineScope() 

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000) 
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % items.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(items[page])
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator(
                        color = Color.LightGray,
                        modifier = Modifier.padding(48.dp)
                    )
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(170.dp)
                    .width(340.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        DotsIndicator(pagerState, items.size)
    }
}

@Composable
fun DotsIndicator(pagerState: PagerState, itemCount: Int) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp), // Add spacing between dots
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(itemCount) { index ->
            val color = if (pagerState.currentPage == index) colorResource(R.color.orange) else Color.LightGray
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
