package com.example.myapplication.presentation.navbar_screens.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun MoreScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = colorResource(R.color.dark_blue))
                    .size(width = 270.dp, height = 100.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "User Name",
                        color = colorResource(R.color.white),
                        fontSize = 17.sp,
                        maxLines = 1
                    )
                    Text(
                        "Email",
                        color = colorResource(R.color.white),
                        fontSize = 14.sp,
                        maxLines = 1
                    )

                }
            }
            Row(modifier.fillMaxWidth()) {
                Spacer(modifier.size(40.dp))
                Image(
                    modifier = modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(3.dp, color = colorResource(R.color.orange), CircleShape),
                    painter = painterResource(R.drawable.client_img),

                    contentDescription = null
                )
            }
        }
        Divider(
            modifier
                .size(width = 300.dp, height = 1.dp)
                .padding(top = 20.dp, bottom = 20.dp),
            color = colorResource(R.color.lighter_grey)
        )

        Spacer(modifier.size(10.dp))
        CustomRow(text = "Your Profile", icon = R.drawable.person_ic)
        Spacer(modifier.size(10.dp))

        CustomRow(text = "Settings", icon = R.drawable.settings_ic)
        Spacer(modifier.size(10.dp))

        CustomRow(text = "Contact Us", icon = R.drawable.phone_ic)
        Spacer(modifier.size(10.dp))

        CustomRow(text = "FAQ", icon = R.drawable.faq_ic)
        Spacer(modifier.size(10.dp))

        CustomRow(text = "About Us", icon = R.drawable.right_ic)
        Spacer(modifier.size(10.dp))

        CustomRow(text = "Terms & Conditions", icon = R.drawable.terms_ic)
        Spacer(modifier.size(10.dp))

        CustomRow(text = "Privacy Policy", icon = R.drawable.privacy)
        Spacer(modifier.size(10.dp))

        CustomRow(text = "Log Out", icon = R.drawable.logout_ic)
        Spacer(modifier.size(10.dp))



    }
}

@Preview
@Composable
private fun MoreScreenPrev() {
    MoreScreen()
}