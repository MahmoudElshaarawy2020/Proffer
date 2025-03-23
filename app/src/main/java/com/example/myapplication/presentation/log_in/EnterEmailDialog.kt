package com.example.myapplication.presentation.log_in

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.presentation.navigation.navbar_screens.more.CustomRow
import com.example.myapplication.presentation.utils.CustomTextField

@Composable
fun EnterEmailDialog(
    onDismiss: () -> Unit,
    onVerifyClick: () -> Unit,
) {

    var email by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    "Forgot Password",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6F00)
                )

                Text(
                    "Enter The Registration Email",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.black)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    Text(
                        "Email",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.black)
                    )
                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        isEmail = true,
                        label = "Your Email",
                        focusedBorderColor = colorResource(id = R.color.lighter_grey),
                        unfocusedBorderColor = colorResource(id = R.color.lighter_grey),
                        cursorColor = colorResource(id = R.color.light_grey),
                        isFocused = false
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                    Button(
                        onClick = {
                            onVerifyClick()
                        },
                        modifier = Modifier
                            .size(width = 220.dp,height = 40.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange))
                    ) {
                        Text(text = "Verify")
                    }
            }
        }
    }
}

@Preview
@Composable
private fun EnterEmailDialogPrev() {
    EnterEmailDialog({}) { }
}

