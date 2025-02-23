//package com.example.myapplication.presentation.navigation.navbar_screens.bids
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.colorResource
//import androidx.navigation.NavController
//import com.example.myapplication.R
//
//@Composable
//fun BidsScreen(modifier: Modifier = Modifier, navController: NavController) {
//    Column(
//        modifier
//            .fillMaxSize()
//            .background(color = colorResource(R.color.light_white)),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Bids Screen")
//    }
//}
//
//@Composable
//fun BidCard(contractorName: String, projectName: String, price: String) {
//    Card(
//        shape = RoundedCornerShape(12.dp),
//        elevation = 4.dp,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 8.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(id = android.R.drawable.sym_def_app_icon),
//                contentDescription = "Contractor Image",
//                modifier = Modifier
//                    .size(50.dp)
//                    .clip(RoundedCornerShape(50)),
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Column(modifier = Modifier.weight(1f)) {
//                Text(text = contractorName, fontWeight = FontWeight.Bold)
//                Text(text = projectName, fontSize = 14.sp, color = Color.Gray)
//                Text(text = price, fontSize = 14.sp, color = Color.Red, fontWeight = FontWeight.Bold)
//            }
//            Button(
//                onClick = { /* Handle accept */ },
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500))
//            ) {
//                Text(text = "Accept", color = Color.White)
//            }
//        }
//    }
//}