package pl.kazoroo.dices.ui.theme.dices

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(viewModel: DicesViewModel = viewModel()) {
    val dice by viewModel.uiState.collectAsState()

    Column {
        SimpleTable(
                columnHeaders = listOf("Points", "You", "Opponent"), rows = listOf(
                listOf("Sum:          ", "", ""),
                listOf("In this round:", "${dice.roundPoints}", ""),
                listOf("Selected:     ", "${dice.points}", "")
        )
        )
        Dices(
                dice = dice.dices,
                isSelected = dice.isSelected,
                onClick = viewModel,
                shouldntExist = dice.shouldntExist
        )
        Buttons(onTurnClick = { viewModel.drawDice() }, onQueueClick = { viewModel.drawDice() })

        Log.d("MainScreen", "dice.skucha - ${dice.skucha}")
    }

    Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        AnimateContent(dice.skucha)
    }
}

@Composable
fun AnimateContent(skucha: Boolean) {
    Box(modifier = Modifier.padding(bottom = 180.dp)) {
        Text(
                text = "SKUCHA!", fontFamily = FontFamily.Monospace, modifier = Modifier
            .background(
                    if (skucha) Color(0x96000000) else Color.Transparent, RoundedCornerShape(8.dp)
            )
            .padding(7.dp), color = if (skucha) Color.Red else Color.Transparent, fontSize = 85.sp
        )
    }
}

@Composable
fun Dices(@DrawableRes dice: List<Int>,
          isSelected: List<Boolean>,
          onClick: DicesViewModel,
          shouldntExist: List<Boolean>) {
    Column(
            Modifier.padding(top = 28.dp, start = 10.dp, end = 10.dp, bottom = 26.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            Image(
                    painter = painterResource(id = dice[0]),
                    contentDescription = "Dice",
                    modifier = Modifier
                        .padding(2.dp)
                        .size(if (!shouldntExist[0]) 110.dp else (-1).dp)
                        .border(
                                if (isSelected[0]) 2.dp else (-1).dp,
                                Color.Black,
                                RoundedCornerShape(4)
                        )
                        .clickable { onClick.isSelectedBehavior(0) })

            Image(painter = painterResource(id = dice[1]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(if (!shouldntExist[1]) 110.dp else (-1).dp)
                .border(if (isSelected[1]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable { onClick.isSelectedBehavior(1) })

            Image(painter = painterResource(id = dice[2]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(if (!shouldntExist[2]) 110.dp else (-1).dp)
                .border(if (isSelected[2]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable { onClick.isSelectedBehavior(2) })
        }

        Row {
            Image(painter = painterResource(id = dice[3]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(if (!shouldntExist[3]) 120.dp else (-1).dp)
                .border(if (isSelected[3]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable { onClick.isSelectedBehavior(3) })

            Image(painter = painterResource(id = dice[4]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(if (!shouldntExist[4]) 120.dp else (-1).dp)
                .border(if (isSelected[4]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable { onClick.isSelectedBehavior(4) })

            Image(painter = painterResource(id = dice[5]), contentDescription = "Dice", modifier = Modifier
                .padding(2.dp)
                .size(if (!shouldntExist[5]) 120.dp else (-1).dp)
                .border(if (isSelected[5]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable { onClick.isSelectedBehavior(5) })
        }
    }
}

@Composable
fun Buttons(modifier: Modifier = Modifier, onQueueClick: () -> Unit, onTurnClick: () -> Unit) {
    Row {
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
            Text(text = "Confirm and complete the throw", modifier = modifier)
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
            itemsIndexed(rows) { _, row ->
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
                .width(width.dp + 65.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
    )
}
