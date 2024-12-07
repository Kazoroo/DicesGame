package pl.kazoroo.dices.shop.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import pl.kazoroo.dices.R
import pl.kazoroo.dices.core.presentation.CoinsViewModel
import pl.kazoroo.dices.core.presentation.components.CoinAmountIndicator
import pl.kazoroo.dices.game.presentation.components.ButtonInfo
import pl.kazoroo.dices.game.presentation.components.DiceButton
import pl.kazoroo.dices.shop.domain.AdManager

@Composable
fun ShopScreen(coinsViewModel: CoinsViewModel) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wooden_background_texture),
            contentDescription = "Wooden background texture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CoinAmountIndicator(
                coinsAmount = coinsViewModel.coinsAmount.collectAsState().value,
                modifier = Modifier.align(Alignment.Start)
            )

            DiceButton(
                buttonInfo = ButtonInfo(
                    text = "Get +100 coins",
                    onClick = {
                        AdManager.showAd(context)
                    }
                ),
                modifier = Modifier
                    .height(dimensionResource(R.dimen.game_button_height))
                    .padding(
                        start = dimensionResource(id = R.dimen.small_padding),
                        end = dimensionResource(id = R.dimen.small_padding),
                        bottom = dimensionResource(R.dimen.buttons_vertical_padding),
                        top = dimensionResource(id = R.dimen.medium_padding)
                    )
            )
        }
    }
}