package pl.kazoroo.dices.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import pl.kazoroo.dices.ui.theme.theme.ColorScheme

@Composable
fun DicesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
            colorScheme = ColorScheme, typography = Typography, shapes = Shapes, content = content
    )
}