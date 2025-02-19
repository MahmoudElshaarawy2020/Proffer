package com.example.myapplication.presentation.navigation.navbar_screens.projects

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R

@Composable
fun ProjectsScreen(modifier: Modifier = Modifier, navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.light_white)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 27.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
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

                IconButton(onClick = { /* Handle notification click */ }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.alarm_ic),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }

            TabsSection(selectedTabIndex) { index ->
                selectedTabIndex = index
            }

            ProjectsList(selectedTabIndex)
        }

        FloatingActionButton(
            onClick = { /* Handle FAB Click */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFFE96C32),
            contentColor = Color.White
        ) {
            Icon(
                painter = painterResource(R.drawable.add_ic),
                contentDescription = "Add Project",
                tint = Color.White
            )
        }
    }
}


data class Project(
    val status: String,
    val name: String,
    val contractor: String,
    val price: String,
    val date: String
)


@Preview(showBackground = true)
@Composable
private fun ProjectsScreenPrev() {
    ProjectsScreen(navController = rememberNavController())
}
