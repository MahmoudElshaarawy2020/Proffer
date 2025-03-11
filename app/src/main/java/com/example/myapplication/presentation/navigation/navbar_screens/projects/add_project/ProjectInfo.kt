package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project

import android.Manifest
import android.app.DatePickerDialog
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
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.input.KeyboardType
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
import java.io.IOException
import java.io.InputStream
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectInfo(
    modifier: Modifier = Modifier,
    viewModel: AddProjectViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
    context: Context
) {

    var selectedProjectId by remember { mutableStateOf<Int?>(null) }
    var projectName by remember { mutableStateOf("") }
    var projectArea by remember { mutableStateOf("") }
    var from_budget by remember { mutableStateOf("") }
    var to_budget by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }
    var long by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var start_date by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var is_open_budget by remember { mutableStateOf("") }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
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
        is Result.Success -> (projectTypesState as Result.Success<ProjectTypesResponse>)
            .data?.data?.mapNotNull { it?.let { project -> project.name to project.id } }
        is Result.Loading -> listOf("Loading..." to null)
        is Result.Error -> listOf("Error fetching types" to null)
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

        ImageUploadBox(imageUris = imageUris, onImagesSelected = { imageUris = it })

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
                    projectTypes!!.forEach { (name, id) ->
                        DropdownMenuItem(
                            text = { Text(name ?: "Unknown") },
                            onClick = {
                                if (id != null) {
                                    selectedOption = name ?: "Unknown"
                                    selectedProjectId = id
                                }
                                expanded = false
                            }
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
                        color = colorResource(R.color.light_pink),
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

        ProjectBudgetSection()


        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = {

                Log.d("ProjectInfo", "Button clicked!")
                val imageParts = prepareFileParts(imageUris, context)
                if (imageParts.isNotEmpty()) {
                    Log.d("ProjectInfo", "Images ready: ${imageParts.size}")
                }
                val requestProjectName = projectName.toRequestBody("text/plain".toMediaType())
                val requestProjectTypeId = selectedProjectId.toString().toRequestBody("text/plain".toMediaType())
                val requestFromBudget = from_budget.ifEmpty { "0" }.toRequestBody("text/plain".toMediaType())
                val requestToBudget = to_budget.ifEmpty { "0" }.toRequestBody("text/plain".toMediaType())
                val requestDuration = duration.ifEmpty { "0" }.toRequestBody("text/plain".toMediaType())
                val requestLocation = location.toRequestBody("text/plain".toMediaType())
                val requestLat = lat.toRequestBody("text/plain".toMediaType())
                val requestLong = long.toRequestBody("text/plain".toMediaType())
                val requestArea = area.ifEmpty { "0" }.toRequestBody("text/plain".toMediaType())
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
                        requestDuration,
                        requestStartDate,
                        requestIsOpenBudget,
                        image = imageParts
                    )
                }

                Log.d("ProjectInfo", "ProjectInfo called")

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

fun prepareFileParts(uris: List<Uri>, context: Context): List<MultipartBody.Part> {
    return uris.mapNotNull { uri ->
        val file = uriToFile(uri, context) // Convert URI to File
        file?.let {
            val requestBody = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images[]", it.name, requestBody) // Use "images[]" for array
        }
    }
}

fun uriToFile(uri: Uri, context: Context): File? {
    val contentResolver = context.contentResolver

    val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)

    try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            tempFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream) // Copy data from URI to the file
            }
        }
        return tempFile
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return null
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
fun ImageUploadBox(imageUris: List<Uri>, onImagesSelected: (List<Uri>) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            onImagesSelected(uris)
            Toast.makeText(context, "${uris.size} Images Selected!", Toast.LENGTH_SHORT).show()
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
                if (imageUris.isEmpty()) colorResource(R.color.light_pink) else Color.Transparent,
                RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (imageUris.isEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Upload Image",
                    tint = Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Project Images",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Upload images in PNG, JPG, JPEG format",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyRow {
                items(  imageUris) { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectBudgetSection() {
    var fromBudget by remember { mutableStateOf("") }
    var toBudget by remember { mutableStateOf("") }
    var openBudget by remember { mutableStateOf(false) }
    var estimatedDays by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Project Budget",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF1D2136)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (!openBudget){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = fromBudget,
                    onValueChange = { fromBudget = it },
                    placeholder = { Text(text = "From") },
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = toBudget,
                    onValueChange = { toBudget = it },
                    placeholder = { Text(text = "To") },
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    singleLine = true
                )
            }
        }else{
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = fromBudget,
                    onValueChange = { fromBudget = it },
                    placeholder = { Text(text = "From") },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    singleLine = true
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = openBudget,
                onCheckedChange = { openBudget = it },
                colors = CheckboxDefaults.colors(colorResource(R.color.orange))
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Open Budget", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Estimated Time",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1D2136)
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = estimatedDays,
                    onValueChange = { estimatedDays = it },
                    placeholder = { Text(text = "Day") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Start Date",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1D2136)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .clickable {
                            showDatePicker(context) { selectedDate ->
                                startDate = selectedDate
                            }
                        }
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (startDate.isEmpty()) "Date" else startDate,
                        fontSize = 14.sp,
                        color = if (startDate.isEmpty()) Color.Gray else Color.Black
                    )

                    Icon(
                        painter = painterResource(R.drawable.calendar_ic),
                        contentDescription = "Calendar Icon",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
    )
    datePicker.show()
}
