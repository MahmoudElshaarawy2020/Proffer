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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.animation.core.tween
import androidx.navigation.compose.rememberNavController
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailsScreen(navController: NavController) {
    var expandedCardId by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedRoom by remember { mutableStateOf("Select Room") }
    val roomTypes = listOf("Bedroom", "Living Room", "Kitchen", "Bathroom")
    val context = LocalContext.current


    val categoryList by remember(selectedRoom) {
        mutableStateOf(
            if (selectedRoom == "Bathroom" || selectedRoom == "Kitchen") getWetList() else getDryList()
        )
    }

    val roomMaterials = listOf("Floor", "Cell", "Wall")
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
    var imageList by remember { mutableStateOf<ArrayList<Uri>>(arrayListOf()) }
    image1?.let { imageList.add(it) }
    image2?.let { imageList.add(it) }
    image3?.let { imageList.add(it) }
    image4?.let { imageList.add(it) }
    image5?.let { imageList.add(it) }
    image6?.let { imageList.add(it) }




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
                    // Room Zone Selection
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
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            OutlinedTextField(
                                value = selectedRoom.ifEmpty { "Select Room" }, // Default text
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.LightGray,
                                    disabledTextColor = Color.Black
                                ),
                                shape = RoundedCornerShape(12.dp),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = null,
                                        modifier = Modifier.clickable { expanded = true }
                                    )
                                }
                            )
                        }

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(color = colorResource(R.color.light_white))
                        ) {
                            roomTypes.forEach { room ->
                                DropdownMenuItem(
                                    text = { Text(room) },
                                    onClick = {
                                        selectedRoom = room
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Room Area Inputs
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

            item {
                Text(
                    text = "Room Material",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    roomMaterials.forEach { material ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .size(100.dp)
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.house_img),
                                contentDescription = material,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = material,
                                color = Color.White,
                                fontSize = 16.sp
                            )

                        }
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
                AdditionCard(
                    category = category,
                    expanded = expandedCardId == category.id,
                    onExpand = {
                        expandedCardId = if (expandedCardId == category.id) null else category.id
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
                            onImageSelected = { uri -> image4= uri },
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
fun AdditionCard(category: CategUI, expanded: Boolean, onExpand: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(16.dp))
            .clickable { onExpand() },
        colors = CardDefaults.cardColors(
            containerColor = if (!expanded) colorResource(R.color.light_white) else colorResource(
                R.color.light_pink
            )
        ),
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

                // Title appears normally when collapsed
                if (!expanded) {
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
            }

            // Animated Visibility for Expanded Content
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(300)) + expandVertically(
                    animationSpec = tween(
                        300
                    )
                ),
                exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(
                    animationSpec = tween(
                        300
                    )
                )
            ) {

                // Move Title to the top of the TextField when expanded
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
                    Column {

                        Text(
                            text = category.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.orange),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, start = 8.dp),
                            textAlign = TextAlign.Start
                        )

                        ExpandedCard(onCollapse = onExpand)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedCard(
    modifier: Modifier = Modifier,
    onCollapse: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val models = listOf("Model A", "Model B", "Model C")
    var selectedModel by remember { mutableStateOf("Model") }
    var amount by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(onClick = onCollapse),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_pink)),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            // Dropdown Menu
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = selectedModel,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { expanded = true })
                    }
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    models.forEach { model ->
                        DropdownMenuItem(
                            text = { Text(model) },
                            onClick = {
                                selectedModel = model
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Amount Input
            OutlinedTextField(
                value = amount,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) amount = newValue
                },
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = price, fontSize = 9.sp, color = Color.Gray)
            }
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


data class CategUI(val id: Int, val img: Int, val name: String, val list: List<AdditionModel>)
data class AdditionModel(val name: String)

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

@Preview
@Composable
private fun RoomDetailsScreenPrev() {
    RoomDetailsScreen(rememberNavController())
}

