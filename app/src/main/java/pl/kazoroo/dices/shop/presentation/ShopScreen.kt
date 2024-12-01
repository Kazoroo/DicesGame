package pl.kazoroo.dices.shop.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import pl.kazoroo.dices.shop.domain.AdManager

@Composable
fun ShopScreen() {
    val context = LocalContext.current

    Button(
        onClick = {
            AdManager.showAd(context)
        }
    ) {
        Text(
            text = "Show add"
        )
    }
}