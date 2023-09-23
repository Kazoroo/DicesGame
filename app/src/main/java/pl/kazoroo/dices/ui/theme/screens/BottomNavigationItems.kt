package pl.kazoroo.dices.ui.theme.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
)

val items = listOf(
        BottomNavigationItem(
                title = "Back",
                icon = Icons.Default.ArrowBack,
        ),
)