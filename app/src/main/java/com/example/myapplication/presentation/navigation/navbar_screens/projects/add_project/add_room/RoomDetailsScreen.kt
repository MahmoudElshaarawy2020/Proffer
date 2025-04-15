package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project.add_room


import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.animation.core.tween
import com.example.myapplication.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.response.AdditionsItem
import com.example.myapplication.data.response.AdditionsResponse
import com.example.myapplication.data.response.RoomZonesResponse
import com.example.myapplication.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailsScreen(
    navController: NavController,
    onMaterialSelected: (Int) -> Unit,
    viewModel: RoomViewModel = hiltViewModel()
) {
    var expandedCardId by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedRoom by remember { mutableStateOf("Select Room") }
    val roomTypesState by viewModel.getRoomZonesState.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Int?>(null) }
    var confirmedAdditions by remember { mutableStateOf<List<AdditionModel>>(emptyList()) }


    val categoryList by remember(selectedRoom) {
        mutableStateOf(
            if (selectedRoom == "Bathroom" || selectedRoom == "Kitchen") getWetList() else getDryList()
        )
    }

    var length by remember { mutableStateOf("") }
    var width by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("0.0") }

    //need to revision
    var image1 by remember { mutableStateOf<Uri?>(null) }
    var image2 by remember { mutableStateOf<Uri?>(null) }
    var image3 by remember { mutableStateOf<Uri?>(null) }
    var image4 by remember { mutableStateOf<Uri?>(null) }
    var image5 by remember { mutableStateOf<Uri?>(null) }
    var image6 by remember { mutableStateOf<Uri?>(null) }
    val imageList = remember(image1, image2, image3, image4, image5, image6) {
        listOfNotNull(image1, image2, image3, image4, image5, image6)
    }

    val roomTypes = when (roomTypesState) {
        is Result.Success -> (roomTypesState as Result.Success<RoomZonesResponse>)
            .data?.data?.mapNotNull { it?.let { project -> project.name to project.id } }

        is Result.Loading -> listOf("Loading..." to null)
        is Result.Error -> listOf("Error fetching types" to null)
        else -> emptyList()
    }

    LaunchedEffect(Unit) {
        viewModel.getRoomZones()
    }

    if (showDialog && selectedCategory != null) {
        MaterialsDialog(
            onDismiss = { showDialog = false },
            onMaterialClick = { material ->
                onMaterialSelected(material?.id ?: 0)
                showDialog = false
            },
            categoryId = selectedCategory!!,
            viewModel = viewModel
        )
    }


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
                    "Add Project",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Room Details",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Enter The Required Data",
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = colorResource(R.color.orange)
                )
            }

            item {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    Text(text = "Room Zone", fontWeight = FontWeight.Bold)

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
                                value = selectedRoom.ifEmpty { "Room zone" },
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
                                roomTypes!!.forEach { (name) ->
                                    DropdownMenuItem(
                                        text = { Text(name ?: "Unknown") },
                                        onClick = {
                                            selectedRoom = name ?: "Unknown"
                                            expanded = false
                                        }
                                    )
                                }
                            }


                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Text(text = "Room Area (㎡)", fontWeight = FontWeight.Bold)
                    RoomDimensionsInput(
                        length = length,
                        width = width,
                        height = height,
                        onLengthChange = { length = it },
                        onWidthChange = { width = it },
                        onHeightChange = { height = it }
                    )
                }
            }

            //Room Materials
            item {
                Column(Modifier.fillMaxSize()) {

                    if (showDialog && selectedCategory != null) {
                        MaterialsDialog(
                            onDismiss = { showDialog = false },
                            onMaterialClick = { showDialog = false },
                            categoryId = selectedCategory!!,
                            viewModel = viewModel
                        )
                    }

                    Text(
                        text = "Room Material",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        MaterialCategoryItem(
                            title = "Floor",
                            imageRes = R.drawable.house_img,
                            onClick = {
                                selectedCategory = 1
                                showDialog = true
                            }
                        )

                        MaterialCategoryItem(
                            title = "Cell",
                            imageRes = R.drawable.house_img,
                            onClick = {
                                selectedCategory = 2
                                showDialog = true
                            }
                        )

                        MaterialCategoryItem(
                            title = "Wall",
                            imageRes = R.drawable.house_img,
                            onClick = {
                                selectedCategory = 3
                                showDialog = true
                            }
                        )
                    }

                }
            }

            // Description Field
            item {
                Text(text = "Description Room", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = {
                        Text(
                            "Tell us more details about this room.",
                            color = Color.LightGray
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Additions
            items(categoryList, key = { it.id }) { category ->
                val confirmedAddition = confirmedAdditions.find { it.categoryId == category.id }

                AdditionCard(
                    category = category,
                    isExpanded = expandedCardId == category.id,
                    onExpand = {
                        expandedCardId = if (expandedCardId == category.id) null else category.id
                    },
                    confirmedAddition = confirmedAddition,
                    onAdditionConfirmed = { addition ->
                        confirmedAdditions =
                            confirmedAdditions.filter { it.categoryId != addition.categoryId } + addition
                        expandedCardId = null
                    }
                )
            }
            // Room Images
            item {
                Text(text = "Room Images", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                        AddImageButton(
                            image1,
                            onImageSelected = { uri -> image1 = uri },
                            context
                        )
                        AddImageButton(
                            image2,
                            onImageSelected = { uri -> image2 = uri },
                            context
                        )
                        AddImageButton(
                            image3,
                            onImageSelected = { uri -> image3 = uri },
                            context
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        AddImageButton(
                            image4,
                            onImageSelected = { uri -> image4 = uri },
                            context
                        )
                        AddImageButton(
                            image5,
                            onImageSelected = { uri -> image5 = uri },
                            context
                        )
                        AddImageButton(
                            image6,
                            onImageSelected = { uri -> image6 = uri },
                            context
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57C00)), // Orange color
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.7f)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Add ")
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                ) {
                                    append(price)
                                }
                                append(" L.E")
                            },
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }

            }


        }
    }
}

@Composable
fun RoomDimensionsInput(
    length: String,
    width: String,
    height: String,
    onLengthChange: (String) -> Unit,
    onWidthChange: (String) -> Unit,
    onHeightChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Length", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Width", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Height", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomOutlinedTextField(
                placeholder = "Length",
                value = length,
                onValueChange = onLengthChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomOutlinedTextField(
                placeholder = "Width",
                value = width,
                onValueChange = onWidthChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomOutlinedTextField(
                placeholder = "Height",
                modifier = Modifier.weight(1f),
                value = height,
                onValueChange = onHeightChange
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    placeholder: String,
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray,
            containerColor = colorResource(R.color.light_white)
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

@Composable
fun AdditionCard(
    category: CategUI,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    confirmedAddition: AdditionModel?,
    onAdditionConfirmed: (AdditionModel) -> Unit
) {
    val backgroundColor = if (isExpanded || confirmedAddition != null) {
        colorResource(R.color.light_pink)
    } else {
        colorResource(R.color.light_white)
    }

    var editingModel by remember { mutableStateOf<AdditionModel?>(null) } // <-- NEW

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(16.dp))
            .clickable {
                editingModel = confirmedAddition
                onExpand()
            },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(category.img),
                    contentDescription = category.name,
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = category.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.dark_grey),
                    modifier = Modifier.weight(1f)
                )
            }

            // Show summary card if confirmed
            confirmedAddition?.let { addition ->
                SummaryCard(
                    addition = addition,
                    onEdit = {
                        editingModel = addition
                        onExpand()
                    }
                )
            }

            // Show expansion content if expanded and no confirmed addition
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                ExpandedCard(
                    category = category,
                    initialModel = confirmedAddition?.let { addition ->
                        AdditionsItem(
                            id = addition.id,
                            name = addition.name,
                            price = addition.price,
                        )
                    },
                    initialAmount = confirmedAddition?.amount?.toString() ?: "",
                    onAdditionConfirmed = {
                        editingModel = null
                        onAdditionConfirmed(it)
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedCard(
    modifier: Modifier = Modifier,
    category: CategUI,
    initialModel: AdditionsItem? = null,
    initialAmount: String = "",
    onAdditionConfirmed: (AdditionModel) -> Unit,
    viewModel: RoomViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedModel by remember (initialModel) {
        mutableStateOf(initialModel)
    }

    var amount by remember (initialAmount) {
        mutableStateOf(initialAmount)
    }


    val additionsState by viewModel.getAdditionsState.collectAsState()

    LaunchedEffect(category.id) {
        viewModel.getAdditions(category.id)
    }

    val additions = when (additionsState) {
        is Result.Success -> (additionsState as Result.Success<AdditionsResponse>).data?.data?.filterNotNull()
        else -> null
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_pink)),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedModel?.name ?: "Select Model",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { expanded = true }
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    additions?.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.name ?: "Unnamed") },
                            onClick = {
                                selectedModel = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { if (it.all(Char::isDigit)) amount = it },
                placeholder = { Text("Amount", color = Color.Gray, fontSize = 12.sp) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Total Price: ${((selectedModel?.price ?: 0.0) * (amount.toIntOrNull() ?: 0))} L.E",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = "Clear",
                    color = colorResource(R.color.orange),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .clickable {
                            selectedModel = null
                            amount = ""
                        }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (selectedModel != null && amount.isNotEmpty()) {
                        val addition = AdditionModel(
                            name = selectedModel!!.name ?: "",
                            price = selectedModel!!.price ?: 0.0,
                            id = selectedModel!!.id ?: 0,
                            amount = amount.toIntOrNull() ?: 0,
                            categoryId = category.id,
                            categoryName = category.name
                        )
                        onAdditionConfirmed(addition)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange)),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Confirm")
            }
        }
    }
}

@Composable
fun SummaryCard(
    addition: AdditionModel,
    onEdit: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_pink)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Category: ${addition.categoryName}", fontSize = 14.sp)
                Text("Edit", color = colorResource(R.color.orange), fontSize = 12.sp)
            }
            Text("Model: ${addition.name}", fontSize = 14.sp)
            Text("Amount: ${addition.amount}", fontSize = 14.sp)
            Text(
                "Total Price: ${addition.price * addition.amount} L.E",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun AddImageButton(
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    context: Context
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onImageSelected(it)
            Toast.makeText(context, "Image Selected!", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFFF3E9)) // Light peach background
            .clickable { launcher.launch("image/*") }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            drawRoundRect(
                color = Color.Gray.copy(alpha = 0.5f),
                size = size,
                style = Stroke(width = 2.dp.toPx(), pathEffect = pathEffect),
                cornerRadius = CornerRadius(16.dp.toPx())
            )
        }

        if (imageUri == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Image",
                    tint = Color.Gray.copy(alpha = 0.7f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Image",
                    fontSize = 12.sp,
                    color = Color.Gray.copy(alpha = 0.7f)
                )
            }
        } else {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun MaterialCategoryItem(title: String, imageRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = title,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}


data class CategUI(
    val id: Int,
    val img: Int,
    val name: String,
    val list: List<AdditionModel>
)

data class AdditionModel(
    val name: String,
    val price: Double,
    val id: Int,
    val amount: Int,
    val categoryId: Int,
    val categoryName: String
)

fun Int.getAdditionIconByID(): Int {
    return when (this) {
        1 -> R.drawable.outlet_img
        2 -> R.drawable.ac_switch_img
        3 -> R.drawable.tele_img
        4 -> R.drawable.data_point
        5 -> R.drawable.bath_tube_img
        6 -> R.drawable.water_sink_img
        7 -> R.drawable.water_mixer
        8 -> R.drawable.water_cabinet
        9 -> R.drawable.water_heater
        else -> R.drawable.outlet_img
    }
}

fun getWetList(): List<CategUI> = listOf(
    CategUI(1, 1.getAdditionIconByID(), "Outlets", emptyList()),
    CategUI(5, 5.getAdditionIconByID(), "Bath Tube", emptyList()),
    CategUI(6, 6.getAdditionIconByID(), "Water Sink", emptyList()),
    CategUI(7, 7.getAdditionIconByID(), "Toilet Cabinet", emptyList()),
    CategUI(8, 8.getAdditionIconByID(), "Water Mixer", emptyList()),
    CategUI(9, 9.getAdditionIconByID(), "Water Heater", emptyList())
)

fun getDryList(): List<CategUI> = listOf(
    CategUI(1, 1.getAdditionIconByID(), "Outlets", emptyList()),
    CategUI(2, 2.getAdditionIconByID(), "AC Switch", emptyList()),
    CategUI(3, 3.getAdditionIconByID(), "Tele Point", emptyList()),
    CategUI(4, 4.getAdditionIconByID(), "Data Point", emptyList())
)