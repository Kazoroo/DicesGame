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
import pl.kazoroo.dices.R
import pl.kazoroo.dices.presentation.game.DicesViewModel

@Composable
fun Dices(viewModel: DicesViewModel) {
    val dicesRows = List(2) { rowIndex ->
        List(3) { columnIndex ->
            val index = rowIndex * 3 + columnIndex
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 28.dp, start = 10.dp, end = 10.dp, bottom = 26.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        dicesRows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEach { info ->
                    Image(
                        painter = painterResource(id = R.drawable.dice_1),
                        contentDescription = "Dice",
                        modifier = Modifier
                            .padding(2.dp)
                            .size(if (true) 110.dp else (-1).dp)
                            .border(
                                if (true) 2.dp else (-1).dp,
                                Color.Black,
                                RoundedCornerShape(4)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {

                            }
                    )
                }
            }
        }
    }
}