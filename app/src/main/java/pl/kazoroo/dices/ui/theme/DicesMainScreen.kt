package pl.kazoroo.dices.ui.theme

import androidx.annotation.DrawableRes
import androidx.annotation.Size
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.kazoroo.dices.R

@Composable
fun MainScreen() {
    val viewModel = viewModel<DicesViewModel>()

    Column {
        SimpleTable(columnHeaders = listOf("Wzium", "Wziummm"), rows = listOf(listOf("112", "2958"), listOf("8", "15"), listOf("2424", "8745")))
        Dices(dice = viewModel.UiState)
        Buttons(onTurnClick = { viewModel.drawDice()  }, onQueueClick = { viewModel.drawDice() })
    }
}

@Composable
fun Dices(@DrawableRes dice: List<Int>) {



    Column(Modifier.padding(top = 28.dp, start = 10.dp, end = 10.dp, bottom = 26.dp), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
        Row() {
            Image(painter = painterResource(id = dice[0]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(110.dp)
                .border(if (isSelected1) 2.dp else (-1).dp, Color.Black)
                .clickable { isSelected1 = !isSelected1 })

            Image(painter = painterResource(id = dice[1]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(110.dp)
                .border(if (isSelected2) 2.dp else (-1).dp, Color.Black)
                .clickable { isSelected2 = !isSelected2 })

            Image(painter = painterResource(id = dice[2]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(110.dp)
                .border(if (isSelected3) 2.dp else (-1).dp, Color.Black)
                .clickable { isSelected3 = !isSelected3 })
        }

        Row() {
            Image(painter = painterResource(id = dice[0]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(120.dp)
                .border(if (isSelected4) 2.dp else (-1).dp, Color.Black)
                .clickable { isSelected4 = !isSelected4 })

            Image(painter = painterResource(id = dice[1]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(120.dp)
                .border(if (isSelected5) 2.dp else (-1).dp, Color.Black)
                .clickable { isSelected5 = !isSelected5 })

            Image(painter = painterResource(id = dice[2]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(120.dp)
                .border(if (isSelected6) 2.dp else (-1).dp, Color.Black)
                .clickable { isSelected6 = !isSelected6 })
        }
    }
}


@Composable
fun Buttons(modifier: Modifier = Modifier, onQueueClick: () -> Unit, onTurnClick: () -> Unit) {
    Row() {
        OutlinedButton(
                onClick =  onQueueClick ,
                modifier = modifier
                    .height(80.dp)
                    .width(40.dp)
                    .weight(1f)
                    .padding(5.dp))  {
            Text(text = "Confirm and end the queue", modifier = modifier)
        }

        Button(
                onClick = onTurnClick,
                modifier = modifier
                    .height(80.dp)
                    .width(40.dp)
                    .weight(1f)
                    .padding(5.dp)) {
            Text(text = "Confirm and end the turn", modifier = modifier
)
        }
    }
}

private fun calcWeights(columns: List<String>, rows: List<List<String>>): List<Float> {
    val weights = MutableList(columns.size) { 0 }
    val fullList = rows.toMutableList()
    fullList.add(columns)
    fullList.forEach { list ->
        list.forEachIndexed { columnIndex, value ->
            weights[columnIndex] = weights[columnIndex].coerceAtLeast(value.length)
        }
    }
    return weights
        .map { it.toFloat() }
}


@Composable
fun SimpleTable(columnHeaders: List<String>, rows: List<List<String>>) {

    val weights = remember { mutableStateOf(calcWeights(columnHeaders, rows)) }

    Column(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
    ) {
        /* HEADER */
        Row(modifier = Modifier.fillMaxWidth()) {
            columnHeaders.forEachIndexed { rowIndex, cell ->
                val weight = weights.value[rowIndex]
                SimpleCell(text = cell, weight = weight)
            }
        }
        /* ROWS  */
        LazyColumn(modifier = Modifier) {
            itemsIndexed(rows) { rowIndex, row ->
                Row(
                        modifier = Modifier
                            .fillMaxWidth()
                ) {
                    row.forEachIndexed { columnIndex, cell ->
                        val weight = weights.value[columnIndex]
                        SimpleCell(text = cell, weight = weight)
                    }
                }
            }
        }
    }
}

@Composable
private fun SimpleCell(
    text: String,
    weight: Float = 1f
) {
    val textStyle = MaterialTheme.typography.body1
    val fontWidth = textStyle.fontSize.value / 2.2f // depends of font used(
    val width = (fontWidth * weight).coerceAtMost(500f)
    val textColor = MaterialTheme.colors.onBackground
    Text(
            text = text,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            color = textColor,
            modifier = Modifier
                .border(0.dp, textColor.copy(alpha = 0.5f))
                .fillMaxWidth()
                .width(width.dp + 160.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
    )
}
