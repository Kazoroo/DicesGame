package pl.kazoroo.dices.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
)

val items = listOf(
    BottomNavigationItem(
            title = "Back",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
    ),
)