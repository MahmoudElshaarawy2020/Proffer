package com.example.myapplication.presentation.navigation.navbar_screens.projects.project_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onTypeClickProject : (Int) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ProjectTypeDialog(
            onDismiss = { showDialog = false },
            onTypeClick = { type ->
                onTypeClickProject(type)
            }
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
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
                        "Projects",
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

            TabsSection(selectedTabIndex) { index ->
                selectedTabIndex = index
            }

            ProjectsList(selectedTabIndex)
        }

        FloatingActionButton(
            onClick = {
                showDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 75.dp, end = 16.dp),
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


