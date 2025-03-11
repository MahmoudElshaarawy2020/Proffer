package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import com.example.myapplication.util.Result
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.location.LocationViewModel
import com.example.myapplication.data.response.ProjectTypesResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectInfo(
    modifier: Modifier = Modifier,
    viewModel: AddProjectViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
    context: Context
) {

    var projectName by remember { mutableStateOf("") }
    var projectArea by remember { mutableStateOf("") }
    var project_type_id by remember { mutableStateOf("") }
    var from_budget by remember { mutableStateOf("") }
    var to_budget by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }
    var long by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var start_date by remember { mutableStateOf("") }
    var is_open_budget by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    val projectTypesState by viewModel.getProjectTypesState.collectAsState()
    val context = LocalContext.current
    val createProjectState by viewModel.createProjectState.collectAsState()
    val dataStoreManager = remember { DataStoreManager(context) }
    val token by dataStoreManager.getToken.collectAsState(initial = null)
    val locationLiveData = locationViewModel.locationDetails.observeAsState()


    LaunchedEffect(locationLiveData.value) {
        locationLiveData.value.let { details ->
            Log.d("LocationDebug", "Details received: $details")
            location = details?.locationName ?: "Unknown"
            lat = details?.latitude?.toString() ?: "Unknown"
            long = details?.longitude?.toString() ?: "Unknown"

            Log.d("ProjectInfo", "Latitude: $lat, Longitude: $long, Location: $location")
        }
    }



    LaunchedEffect(createProjectState) {
        when (createProjectState) {
            is Result.Loading -> Toast.makeText(context, "Creating project...", Toast.LENGTH_SHORT)
                .show()

            is Result.Success -> {
                Toast.makeText(context, "Project created successfully!", Toast.LENGTH_LONG).show()
            }

            is Result.Error -> Toast.makeText(
                context,
                "Error: ${(createProjectState as Result.Error).message}",
                Toast.LENGTH_LONG
            ).show()

            else -> {}
        }
    }

    val projectTypes = when (projectTypesState) {
        is Result.Success -> (projectTypesState as Result.Success<ProjectTypesResponse>).data?.data?.map { it?.name }
        is Result.Loading -> listOf("Loading...")
        is Result.Error -> listOf("Error fetching types")
        else -> emptyList()
    }

    LaunchedEffect(Unit) {
        viewModel.getProjectTypes()
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            locationViewModel.getDeviceLocation() // Fetch location if permission granted
        } else {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

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

        ImageUploadBox(imageUri) { selectedUri ->
            imageUri = selectedUri
        }
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Project Name",
            placeholder = "Project Name",
            value = projectName,
            onValueChange = { projectName = it },
            modifier = modifier.fillMaxWidth()
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
                    .background(
                        color = colorResource(R.color.light_white),
                        RoundedCornerShape(12.dp)
                    )
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
                    modifier = Modifier.background(color = colorResource(R.color.light_white))
                ) {
                    projectTypes?.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                if (option != null) {
                                    Text(option)
                                }
                            },
                            onClick = {
                                if (option != null) {
                                    selectedOption = option
                                }
                                expanded = false
                            },
                            modifier = Modifier
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
            isNumber = true,
            modifier = modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(8.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Project Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF1D2136)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        placeholder = { Text(text = "Project Location") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            locationViewModel.getDeviceLocation()
                        } else {
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 20.dp)
                        .background(
                            color = Color(0xFFFFF1E8),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.location_ic),
                        contentDescription = "Location Icon",
                        tint = Color.Black
                    )
                }
            }




        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = {

                Log.d("EditProfileScreen", "Button clicked!")
                val imagePart = prepareFilePart(imageUri, context)
                if (imagePart != null) {
                    Log.d("ProjectInfo", "Image part created: ${imagePart.body.contentType()}")
                }

                val requestProjectName = projectName.toRequestBody("text/plain".toMediaType())
                val requestProjectTypeId = project_type_id.toRequestBody("text/plain".toMediaType())
                val requestFromBudget = from_budget.toRequestBody("text/plain".toMediaType())
                val requestToBudget = to_budget.toRequestBody("text/plain".toMediaType())
                val requestLocation = location.toRequestBody("text/plain".toMediaType())
                val requestLat = lat.toRequestBody("text/plain".toMediaType())
                val requestLong = long.toRequestBody("text/plain".toMediaType())
                val requestArea = area.toRequestBody("text/plain".toMediaType())
                val requestStartDate = start_date.toRequestBody("text/plain".toMediaType())
                val requestIsOpenBudget = is_open_budget.toRequestBody("text/plain".toMediaType())



                token?.let {
                    viewModel.createProject(
                        it,
                        requestProjectName,
                        requestProjectTypeId,
                        requestFromBudget,
                        requestToBudget,
                        requestLocation,
                        requestLat,
                        requestLong,
                        requestArea,
                        requestStartDate,
                        requestIsOpenBudget,
                        image = imagePart
                    )
                }

                Log.d("EditProfileScreen", "editYourProfile called")

            },
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

fun prepareFilePart(uri: Uri?, context: Context): MultipartBody.Part? {
    uri ?: return null
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, getFileName(context, uri))
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()

    val requestFile = file.asRequestBody(contentResolver.getType(uri)?.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("project_image", file.name, requestFile)
}


fun getFileName(context: Context, uri: Uri): String {
    var name = "temp_image"
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst()) {
            name = it.getString(nameIndex)
        }
    }
    return name
}

@Composable
fun ImageUploadBox(imageUri: Uri?, onImageSelected: (Uri?) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageSelected(uri)
        uri?.let {
            Toast.makeText(context, "Image Selected!", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(8.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable { launcher.launch("image/*") }
            .background(
                if (imageUri == null) colorResource(R.color.light_pink) else Color.Transparent,
                RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Upload Image",
                    tint = Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Project Image",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Upload it in Format \n PNG, JPG, JPEG",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        } else {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}