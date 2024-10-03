package pl.kazoroo.dices.presentation.game.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import pl.kazoroo.dices.R
import pl.kazoroo.dices.presentation.components.ButtonInfo
import pl.kazoroo.dices.presentation.components.DiceButton

@Composable
fun GameButtons(buttonsInfo: List<ButtonInfo>) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        buttonsInfo.forEach { buttonInfo ->
            DiceButton(
                buttonInfo,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.xlarge_padding))
                    .weight(0.5f)
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.small_padding),
                        vertical = dimensionResource(R.dimen.buttons_vertical_padding)
                    )
            )
        }
    }
}