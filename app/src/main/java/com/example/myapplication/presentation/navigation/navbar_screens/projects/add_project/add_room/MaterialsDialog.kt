package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project.add_room

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
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
import com.example.myapplication.util.Result
import com.example.myapplication.data.response.MaterialItem
import com.example.myapplication.data.response.MaterialsResponse

@Composable
fun MaterialsDialog(
    onDismiss: () -> Unit,
    onMaterialClick: (MaterialItem?) -> Unit,
    categoryId: Int,
    viewModel: RoomViewModel = hiltViewModel()
) {
    val materialsState by viewModel.getMaterialsState.collectAsState()
    var selectedMaterial by remember { mutableStateOf<MaterialItem?>(null) }

    LaunchedEffect(categoryId) {
        viewModel.getMaterials(categoryId)
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Material Name",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6F00)
                )

                Text(
                    text = "Select Your Favorite Material",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {


                    when (materialsState) {
                        is Result.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = Color(0xFFFF6F00))
                        }

                        is Result.Success -> {
                            val materials =
                                (materialsState as Result.Success<MaterialsResponse>).data?.data
                                    ?: emptyList()

                            if (materials.isEmpty()) {
                                Text("No materials available", color = Color.Gray)
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(materials) { material ->
                                        if (material != null) {
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

                        is Result.Idle -> {
                            Text("No data available", color = Color.Gray)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Confirm Button
                Button(
                    onClick = { onMaterialClick(selectedMaterial) },
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .height(50.dp)
                        .width(220.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6F00)
                    ),
                    shape = RoundedCornerShape(25.dp),
                    enabled = selectedMaterial != null
                ) {
                    Text(
                        text = "Add ${selectedMaterial?.price ?: 0} L.E",
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
            .size(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .border(
                width = if (isSelected) 4.dp else 0.dp,
                color = if (isSelected) Color(0xFFFF6F00) else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        AsyncImage(
            model = material.materialImage,
            contentDescription = "Material Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )

        // Text overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                material.name?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        modifier = Modifier.basicMarquee()
                    )
                }
                Text(
                    text = "${material.price} M²",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
        }
    }
}
