package pl.kazoroo.dices.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.kazoroo.dices.data.DicePreferencesState
import pl.kazoroo.dices.data.PreferencesViewModel
import pl.kazoroo.dices.ui.theme.theme.Purple700
import pl.kazoroo.dices.ui.theme.theme.Teal200

@Composable
private fun getUserPreferencesAppColorFromPreferences(preferencesState: DicePreferencesState): ColorScheme {
    val appColorRGB = Color(
            preferencesState.layoutColor.elementAt(0).toFloat(),
            preferencesState.layoutColor.elementAt(1).toFloat(),
            preferencesState.layoutColor.elementAt(2).toFloat(),
    )
    return if (isSystemInDarkTheme()) {
        darkColorScheme(
                primary = appColorRGB, secondary = Purple700, tertiary = Teal200
        )
    }
    else {
        lightColorScheme(
                primary = appColorRGB, secondary = Purple700, tertiary = Teal200
        )
    }
}

@Composable
fun DicesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    preferencesViewModel: PreferencesViewModel = viewModel(factory = PreferencesViewModel.Factory),
    content: @Composable () -> Unit,
) {
    val colorScheme = getUserPreferencesAppColorFromPreferences(
            preferencesViewModel.preferencesState.collectAsState().value
    )
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
            colorScheme = colorScheme, typography = Typography, content = content
    )
}