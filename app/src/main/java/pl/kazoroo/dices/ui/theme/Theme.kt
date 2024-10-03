package pl.kazoroo.dices.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


@Composable
fun DicesTheme(
    content: @Composable () -> Unit,
) {
    val colorScheme = lightColorScheme(
        primary = Purple700,
        secondary = Purple700,
        tertiary = Teal200
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}