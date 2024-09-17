package pl.kazoroo.dices.presentation.components

import androidx.compose.ui.Modifier

data class ButtonInfo(
    val text: String,
    val modifier: Modifier = Modifier,
    val onClick: () -> Unit
)