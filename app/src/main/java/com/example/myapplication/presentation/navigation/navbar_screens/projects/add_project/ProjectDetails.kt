package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.navigation.Screen


@Composable
fun ProjectDetails(modifier: Modifier = Modifier,navController: NavController) {

//    val rooms = List(6) { index ->
//        Room(
//            name = if (index % 2 == 0) "Bedroom" else "Bathroom",
//            size = "143*154*123 M²",
//            price = "7.000 LE"
//        )
//    }

    var price by remember { mutableStateOf(0.0) }
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate(Screen.RoomDetails.route)},
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, color = colorResource(R.color.orange)),
            modifier = Modifier
                .shadow(8.dp,RoundedCornerShape(16.dp))
                .size(width = 340.dp, height = 60.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("+ New Room", color = Color(0xFFE96C32), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .background(color = colorResource(R.color.lighter_white))
        ) {
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                contentPadding = PaddingValues(8.dp)
//            ) {
//                items(rooms.size) { index ->
//                    RoomCard(rooms[index])
//                }
//            }
        }

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange)),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .height(60.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(20.dp),
                    clip = true
                )
                .padding(vertical = 8.dp)
        ) {
            Text(
                "Save $price LE",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }

}

@Composable
fun RoomCard(room: Room) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .background(color = colorResource(R.color.light_white))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(room.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(room.size, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(room.price, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))


            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.light_white)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(width = 70.dp, height = 30.dp)
            ) {

                Text(
                    "Edit",
                    color = colorResource(R.color.orange),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}

data class Room(val name: String, val size: String, val price: String)

