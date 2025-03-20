package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project.add_room

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.util.Result
import com.example.myapplication.data.response.MaterialItem

@Composable
fun MaterialsDialog(
    onDismiss: () -> Unit,
    onVerifyClick: (MaterialItem?) -> Unit,
    categoryId: Int,
    viewModel: RoomViewModel = hiltViewModel()
) {
    val materialsState by viewModel.getMaterialsState.collectAsState()
    var selectedMaterial by remember { mutableStateOf<MaterialItem?>(null) }
    var price by remember { mutableStateOf(0.0) }

    LaunchedEffect(categoryId) {
        viewModel.getMaterials(categoryId)
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Material Name",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6F00)
                )

                Text(
                    text = "Select Your Favorite Material",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.black)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Handle API states (Loading, Success, Error)
                when (materialsState) {
                    is Result.Loading -> {
                        CircularProgressIndicator(color = Color(0xFFFF6F00))
                    }

                    is Result.Success -> {
                        val materials = (materialsState as Result.Success<List<MaterialItem>>).data

                        if (materials != null) {
                            if (materials.isEmpty()) {
                                Text("No materials available", color = Color.Gray)
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.height(400.dp)
                                ) {
                                    items(materials) { material ->
                                        MaterialItemCard(
                                            material = material,
                                            isSelected = selectedMaterial == material,
                                            onClick = { selectedMaterial = material }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is Result.Error -> {
                        Text(
                            text = "Error: ${(materialsState as Result.Error).message}",
                            color = Color.Red
                        )
                    }

                    is Result.Idle -> TODO()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Button
                Button(
                    onClick = { onVerifyClick(selectedMaterial) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF8C00) // Orange button color
                    ),
                    shape = RoundedCornerShape(25.dp),
                    enabled = selectedMaterial != null
                ) {
                    Text(
                        text = "Add $price L.E",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MaterialItemCard(
    material: MaterialItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFFFF8C00) else Color.White)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = material.materialImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}
