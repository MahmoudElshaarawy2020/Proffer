package com.example.myapplication.presentation.navigation.navbar_screens.more.contact_us

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var contactType by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val contactOptions = listOf("Technical Support", "General Inquiry")

    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white))
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        TopAppBar(
            title = {
                Text(
                    "Contact Us",
                    modifier = Modifier.fillMaxWidth(),
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
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Dropdown List
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = contactType,
                onValueChange = { contactType = it },
                label = { Text("Contact Type") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(R.color.orange),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = colorResource(R.color.orange),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = colorResource(R.color.orange)
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(color = colorResource(R.color.light_white))
            ) {
                contactOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            contactType = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Large OutlinedTextField for Message
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Your Message") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            maxLines = 5,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(R.color.orange),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = colorResource(R.color.orange),
                unfocusedLabelColor = Color.Gray,
                cursorColor = colorResource(R.color.orange)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = { /* Handle submission logic here */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange))
        ) {
            Text("Submit")
        }
    }
}
