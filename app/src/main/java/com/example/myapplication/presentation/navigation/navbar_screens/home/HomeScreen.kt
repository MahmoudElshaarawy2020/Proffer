
package com.example.myapplication.presentation.navigation.navbar_screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onNotificationClick : () -> Unit = {}
) {
    val backgroundColor = colorResource(R.color.light_white)
    val systemUiController = rememberSystemUiController()


    val product = Product(
        images = listOf(
            R.drawable.sample_project_img,
            R.drawable.sample_project_img,
            R.drawable.sample_project_img
        )
    )

    val contractors = listOf("Contractor 1", "Contractor 2", "Contractor 3", "Contractor 4")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white)),
    ) {
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


            ImageProductPager(product)

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
                items(contractors.size) { contractor ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.client_img),
                            contentDescription = "Contractor Image",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Black, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "Contractor name",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.Yellow
                            )
                            Text(text = "4.8", fontSize = 12.sp)
                        }
                    }
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


data class Product(
    val images: List<Int>
)

@Preview
@Composable
private fun HomeScreenPrev() {
    HomeScreen(rememberNavController())
}






