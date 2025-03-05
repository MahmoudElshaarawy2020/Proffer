package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectInfo(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    var projectName by remember { mutableStateOf("") }
    var projectArea by remember { mutableStateOf("") }

    val options = listOf("Apartment", "Residential building", "Villa")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        ImageUploadBox()

        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Project Name",
            placeholder = "Project Name",
            value = projectName,
            onValueChange = { projectName = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Project kind",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            color = Color(0xFF1A1B2F)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .clickable { expanded = true }
                    .background(color = colorResource(R.color.light_white), RoundedCornerShape(12.dp))
            ) {
                OutlinedTextField(
                    value = selectedOption.ifEmpty { "Project Name" },
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable { expanded = true }
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(color = colorResource(R.color.light_white))
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedOption = option
                                expanded = false
                            },
                            modifier = Modifier.shadow(4.dp, RoundedCornerShape(8.dp)) // Adding elevation
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            label = "Project Area (M²)",
            placeholder = "Project Area",
            value = projectArea,
            onValueChange = { projectArea = it },
            isNumber = true
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(200.dp)
                .height(50.dp)
                .shadow(4.dp, RoundedCornerShape(32.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange)),
            shape = RoundedCornerShape(32.dp)
        ) {
            Text(text = "Step 2", color = Color.White, fontSize = 18.sp)
        }
    }
}
