package com.example.myapplication.presentation.navigation.navbar_screens.projects.project_home

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


@Composable
fun ProjectItem(project: Project) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.light_white)),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(140.dp),
                painter = painterResource(R.drawable.project_img),
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = project.status, color = colorResource(R.color.light_grey))
                Text(text = project.date, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = project.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = project.price, fontWeight = FontWeight.Bold, fontSize = 16.sp)

            }
            Row(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.client_img),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
                Text(
                    text = project.contractor,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}