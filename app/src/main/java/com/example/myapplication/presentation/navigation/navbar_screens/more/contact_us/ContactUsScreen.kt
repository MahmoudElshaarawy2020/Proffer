package com.example.myapplication.presentation.navigation.navbar_screens.more.contact_us

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.request.ContactUsRequest
import com.example.myapplication.util.Result
import com.example.myapplication.presentation.navigation.navbar_screens.more.MoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MoreViewModel = hiltViewModel()
) {
    var contactType by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val contactTypesState by viewModel.getContactTypesState.collectAsState()
    val contactUsState by viewModel.contactUsState.collectAsState()
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val token by dataStoreManager.getToken.collectAsState(initial = null)

    var message by remember { mutableStateOf("") }

    val contactUs = ContactUsRequest(contactType, message)


    LaunchedEffect(key1 = true) {
        viewModel.getContactTypes()
    }

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

        val contactTypes = when (contactTypesState) {
            is Result.Success -> contactTypesState.data?.data
                ?.filterNotNull()
                ?.mapNotNull { it.id?.let { id -> id to it.name } }
                ?: emptyList()
            else -> emptyList()
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = contactTypes.find { it.first == contactType }?.second ?: "Select Contact Type",
                onValueChange = { },
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
                readOnly = true,
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
                contactTypes.forEach { (id, name) ->
                    DropdownMenuItem(
                        text = {
                            if (name != null) {
                                Text(name)
                            }
                        },
                        onClick = {
                            contactType = id
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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

        Button(
            onClick = {
                if (contactType != 0 && message.isNotBlank()) {
                    token?.let { viewModel.contactUs(it, contactUs) }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange))
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (contactUsState) {
            is Result.Success -> {

                Toast.makeText(context, "Message sent successfully!", Toast.LENGTH_SHORT).show()
                LaunchedEffect(contactUsState) {
                    contactType = 0
                    message = ""
                }
            }

            is Result.Error -> {

                Toast.makeText(context, "Failed to send message!", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }
}


