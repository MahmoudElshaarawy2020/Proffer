package com.example.myapplication.presentation.navigation.navbar_screens.bottom_navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.presentation.navigation.navbar_screens.navbar_items.BottomNavItem

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(32.dp))
            .height(70.dp),
        containerColor = colorResource(R.color.light_pink),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = false,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        modifier = Modifier.size(24.dp),
                        contentDescription = item.label,
                        tint = if (selectedItem == index) colorResource(id = R.color.orange) else colorResource(
                            id = R.color.black
                        )
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = if (selectedItem == index) colorResource(id = R.color.orange) else Color.Black
                    )
                },
                modifier = Modifier.padding(vertical = 0.dp),
                alwaysShowLabel = true
            )
        }
    }
}