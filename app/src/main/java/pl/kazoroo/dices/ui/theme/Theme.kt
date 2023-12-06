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

fun stringSetToColor(
    layoutColor: Set<String>
): Color {
    var appColor = Color(21, 32, 28)

    try {
        appColor = Color(
                layoutColor.elementAt(0).toFloat(),
                layoutColor.elementAt(1).toFloat(),
                layoutColor.elementAt(2).toFloat(),
        )
    } catch (e: IndexOutOfBoundsException) {
        e.printStackTrace()
    }

    return appColor
}

@Composable
private fun getAppColorSchemeFromPreferences(
        preferencesState: DicePreferencesState
): ColorScheme {
    val appColorRGB = stringSetToColor(preferencesState.layoutColor)

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
    val colorScheme = getAppColorSchemeFromPreferences(
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