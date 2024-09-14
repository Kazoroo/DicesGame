package pl.kazoroo.dices.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pl.kazoroo.dices.R

val imFellEnglish = FontFamily(
    Font(R.font.imfelldwpica_regular, FontWeight.Normal),
    Font(R.font.imfelldwpica_italic, FontWeight.Normal, FontStyle.Italic)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = imFellEnglish,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = Color.Black
    ),

    labelLarge = TextStyle(
        fontFamily = imFellEnglish,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )
)