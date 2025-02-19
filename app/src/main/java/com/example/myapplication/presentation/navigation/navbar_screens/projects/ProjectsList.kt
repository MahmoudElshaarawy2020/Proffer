package com.example.myapplication.presentation.navigation.navbar_screens.projects


import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ProjectsList(selectedTabIndex: Int) {
    val currentProjects = listOf(
        Project("In progress", "Project Alpha", "Contractor Ahmed", "15000 L.E", "12/3/2023"),
        Project("In progress", "Project Beta", "Contractor Karim", "20000 L.E", "14/3/2023"),
        Project("In progress", "Project Beta", "Contractor Karim", "20000 L.E", "14/3/2023"),
        Project("In progress", "Project Beta", "Contractor Karim", "20000 L.E", "14/3/2023"),
        Project("In progress", "Project Beta", "Contractor Karim", "20000 L.E", "14/3/2023"),
        Project("In progress", "Project Beta", "Contractor Karim", "20000 L.E", "14/3/2023"),
        Project("In progress", "Project Beta", "Contractor Karim", "20000 L.E", "14/3/2023"),
    )

    val historyProjects = listOf(
        Project("Completed", "Old Project X", "Contractor Ziad", "10000 L.E", "10/2/2023"),
        Project("Completed", "Old Project Y", "Contractor Omar", "18000 L.E", "8/2/2023"),
        Project("Completed", "Old Project Y", "Contractor Omar", "18000 L.E", "8/2/2023"),
        Project("Completed", "Old Project Y", "Contractor Omar", "18000 L.E", "8/2/2023"),
        Project("Completed", "Old Project Y", "Contractor Omar", "18000 L.E", "8/2/2023"),
        Project("Completed", "Old Project Y", "Contractor Omar", "18000 L.E", "8/2/2023"),
        Project("Completed", "Old Project Y", "Contractor Omar", "18000 L.E", "8/2/2023"),
    )

    val projectsToShow = if (selectedTabIndex == 0) currentProjects else historyProjects

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(projectsToShow) { project ->
            ProjectItem(project)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
