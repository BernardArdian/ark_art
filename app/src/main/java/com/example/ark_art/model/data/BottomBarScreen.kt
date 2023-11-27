package com.example.ark_art.model.data

import androidx.compose.ui.graphics.vector.ImageVector


data class BottomBarScreen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
