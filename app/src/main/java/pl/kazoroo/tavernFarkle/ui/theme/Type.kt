package pl.kazoroo.tavernFarkle.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pl.kazoroo.tavernFarkle.R

val imFellEnglish = FontFamily(
    Font(R.font.imfelldwpica_regular, FontWeight.Normal),
    Font(R.font.imfelldwpica_italic, FontWeight.Normal, FontStyle.Italic)
)

val fenwickWoodtype = FontFamily(
    Font(R.font.fenwick_woodtype, FontWeight.Bold)
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
    ),

    displayLarge = TextStyle(
        fontFamily = imFellEnglish,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        shadow = Shadow(color = Color.Black)
    ),

    titleLarge = TextStyle(
        fontFamily = fenwickWoodtype,
        fontSize = 80.sp,
        shadow = Shadow(color = Color.Black),
        color = Color.White
    ),

    titleSmall = TextStyle(
        fontFamily = imFellEnglish,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
)