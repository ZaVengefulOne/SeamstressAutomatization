package com.example.seamstressautomatization.utilities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import com.example.seamstressautomatization.models.BottomNavItem

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Сводная",
            icon = Icons.Filled.Home,
            route = "main"
        ),
        BottomNavItem(
            label = "Сотрудники",
            icon = Icons.Filled.Face,
            route = "stuff"
        ),
        BottomNavItem(
            label = "Одежда",
            icon = Icons.Filled.List,
            route = "clothes"
        ),
        BottomNavItem(
            label = "Операции",
            icon = Icons.Filled.Build,
            route = "operations"
        )

    )
}