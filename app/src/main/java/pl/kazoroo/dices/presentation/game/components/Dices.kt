package pl.kazoroo.dices.presentation.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.kazoroo.dices.domain.model.DiceInfo

@Composable
fun Dices(
    diceState: DiceInfo,
    diceOnClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 28.dp, start = 10.dp, end = 10.dp, bottom = 26.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in 0..1) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                for (column in 0..2) {
                    val index = row * 3 + column

                    Image(
                        painter = painterResource(id = diceState.diceList[index].image),
                        contentDescription = "Dice",
                        modifier = Modifier
                            .padding(2.dp)
                            .size(if (diceState.isDiceVisible[index]) 110.dp else (-1).dp)
                            .border(
                                if (diceState.isDiceSelected[index]) 2.dp else (-1).dp,
                                Color.Red,
                                RoundedCornerShape(100)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                diceOnClick(index)
                            }
                    )
                }
            }
        }
    }
}