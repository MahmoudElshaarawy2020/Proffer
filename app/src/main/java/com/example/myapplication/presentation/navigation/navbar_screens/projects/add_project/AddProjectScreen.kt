package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.util.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(
    navController: NavController,
    viewModel: AddProjectViewModel = hiltViewModel()
) {

    var selectedOption by remember { mutableStateOf("Project Info") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            title = { Text(
                "Add Project",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 35.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold) },
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter The Required Data",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProjectTabs(selectedOption, onTabSelected = { selectedOption = it })

        when (selectedOption) {
            "Project Info" -> ProjectInfo(context = LocalContext.current)
            "Project Details" -> ProjectDetails()
        }


    }
}

@Composable
fun ProjectTabs(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TabItem(title = "Project Info", isSelected = selectedTab == "Project Info") {
            onTabSelected("Project Info")
        }
        TabItem(title = "Project Details", isSelected = selectedTab == "Project Details") {
            onTabSelected("Project Details")
        }
    }
}




@Composable
fun TabItem(title: String, isSelected: Boolean, onClick: () -> Unit = {}) {
    val backgroundColor = if (isSelected) colorResource(R.color.lighter_white) else colorResource(R.color.light_white)
    val borderColor = if (isSelected) colorResource(R.color.orange) else Color.Gray

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = borderColor)
            ) { onClick() }
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.right_ic),
                contentDescription = null,
                tint = borderColor
            )
            Text(
                text = title,
                color = borderColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(modifier: Modifier = Modifier, label: String, placeholder: String, value: String, onValueChange: (String) -> Unit, isNumber: Boolean = false) {
    Column {
        Text(
            text = label,
            modifier = Modifier.padding(vertical = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp, color = Color.Black)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            ),
            placeholder = { Text(text = placeholder) },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text
            ),
            modifier = modifier,
            shape = RoundedCornerShape(12.dp)
        )
    }
}


