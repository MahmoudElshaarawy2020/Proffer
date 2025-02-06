package com.example.myapplication.ui.presentation.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = modifier
                .size(width = 190.dp, height = 50.dp)
                .padding(bottom = 8.dp),
            painter = painterResource(id = R.drawable.logo_img),
            contentDescription = null
        )

        Text(
            text = "Create Your Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.orange),
            lineHeight = 28.sp
        )
        Text(
            text = "Get exclusive offers",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.dark_grey),
            lineHeight = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    SignUpScreen()
}