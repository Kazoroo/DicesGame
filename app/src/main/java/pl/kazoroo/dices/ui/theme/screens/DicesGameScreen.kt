package pl.kazoroo.dices.ui.theme.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.kazoroo.dices.data.DicesViewModel
import pl.kazoroo.dices.navigation.items

@Composable
fun calculateButtonsSize(): Array<Int> {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    Log.d("calculateButtonsSize", "screenWidth - $screenWidth")

    val buttonWidth = screenWidth / 2
    Log.d("calculateButtonsSize", "buttonWidth - $buttonWidth")

    val buttonHeight = buttonWidth / 1.61803398875
    Log.d("calculateButtonsSize", "buttonHeight - $buttonHeight")

    return arrayOf(buttonWidth, buttonHeight.toInt())
}


/**
 * Creates a game screen
 * @param viewModel viewmodel for calculations
 * @param navController needed to navigate between screens
 * @return nothing
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun GameScreen(viewModel: DicesViewModel = viewModel(), navController: NavController) {
    val dice by viewModel.uiState.collectAsState()
    val buttonSize = calculateButtonsSize()
    val isShowingExitDialog = remember { mutableStateOf(false) }

    if (isShowingExitDialog.value) {
        AlertDialog(onDismissRequest = { isShowingExitDialog.value = false }, confirmButton = {
            TextButton(
                    onClick = { isShowingExitDialog.value = false; navController.navigateUp() },
                    modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Yes")
            }
        }, dismissButton = {
            TextButton(
                    onClick = { isShowingExitDialog.value = false },
                    modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "No")
            }
        }, text = {
            Text(text = "Do you really want to leave a match? It will take 30 coins.")
        }, title = {
            Text(text = "Leaving a match")
        })
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        NavigationBar {
            items.forEachIndexed { _, item ->
                NavigationBarItem(selected = false, onClick = {
                    isShowingExitDialog.value = true
                }, label = {
                    Text(text = item.title)
                }, icon = {
                    Icon(
                            imageVector = item.icon, contentDescription = item.title
                    )
                })
            }
        }
    }) {

    }

    Column {
        Table(
                columnHeaders = listOf("Points", "You", "Opponent"), rows = listOf(
                listOf("Sum:          ", "${dice.sumOfPoints}/2000", ""),
                listOf("    In this round:", "${dice.roundPoints}", ""),
                listOf("  Selected:     ", "${dice.points}", "")
        )
        )
        Dices(
                dice = dice.dices,
                isSelected = dice.isSelected,
                onClick = viewModel,
                diceShouldntExist = dice.shouldntDiceExist
        )
        Buttons(
                onQueueClick = { viewModel.queueEndBehavior() },
                onRoundClick = { viewModel.roundEndBehavior() },
                weight = buttonSize[0],
                height = buttonSize[1]
        )
    }

    if (dice.skucha) {
        SkuchaScreen(showSkucha = dice.showSkucha, sumOfPoints = dice.sumOfPoints)
        viewModel.showSkuchaTextBehavior()
    }
}

@Composable
fun GameResultScreen(text: String, fontColor: Color) {
    Box(modifier = Modifier.padding(bottom = 180.dp), contentAlignment = Alignment.Center) {
        Text(
                text = text,
                color = fontColor,
                fontSize = 85.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .background(Color(0x96000000), RoundedCornerShape(8.dp))
                    .padding(7.dp),
        )
    }
}

@Composable
fun SkuchaScreen(showSkucha: Boolean, sumOfPoints: Int) {
    if (showSkucha) {
        if (sumOfPoints >= 2000) {
            GameResultScreen("Win!", Color(0xff6fd633))
        }
        else {
            GameResultScreen("SKUCHA!", Color.Red)
        }
    }
}

data class DicesInfo(val dice: Int,
                     val isSelected: Boolean,
                     val onClick: () -> Unit,
                     val shouldntExist: Boolean)

@Composable
fun Dices(@DrawableRes dice: List<Int>,
          isSelected: List<Boolean>,
          onClick: DicesViewModel,
          diceShouldntExist: List<Boolean>) {

    val dicesRows = List(2) { rowIndex ->
        List(3) { columnIndex ->
            val index = rowIndex * 3 + columnIndex
            DicesInfo(
                    dice = dice[index],
                    isSelected = isSelected[index],
                    onClick = { onClick.isSelectedBehavior(index) },
                    shouldntExist = diceShouldntExist[index]
            )
        }
    }

    Column(
            Modifier
                .padding(top = 28.dp, start = 10.dp, end = 10.dp, bottom = 26.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        dicesRows.forEach { row ->
            Row(
                    horizontalArrangement = Arrangement.Center
            ) {
                row.forEachIndexed { _, info ->
                    Image(
                            painter = painterResource(id = info.dice),
                            contentDescription = "Dice",
                            modifier = Modifier
                                .padding(2.dp)
                                .size(if (!info.shouldntExist) 110.dp else (-1).dp)
                                .border(
                                        if (info.isSelected) 2.dp else (-1).dp,
                                        Color.Black,
                                        RoundedCornerShape(4)
                                )
                                .clickable(indication = null,
                                        interactionSource = remember { MutableInteractionSource() }) {
                                    info.onClick()
                                })
                }
            }
        }
    }
}

@Composable
fun Buttons(modifier: Modifier = Modifier,
            onRoundClick: () -> Unit,
            onQueueClick: () -> Unit,
            weight: Int,
            height: Int) {
    Row {
        OutlinedButton(
                onClick = onQueueClick,
                shape = RoundedCornerShape(15.dp),
                modifier = modifier
                    .height(height.dp)
                    .width(weight.dp)
                    .padding(start = 5.dp, end = 5.dp)
        ) {
            Text(
                    text = "Confirm and end the queue",
                    modifier = modifier,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
            )
        }
        Button(
                onClick = onRoundClick,
                shape = RoundedCornerShape(15.dp),
                modifier = modifier
                    .height(height.dp)
                    .width(weight.dp)
                    .padding(start = 5.dp, end = 5.dp)
        ) {
            Text(
                    text = "Confirm and complete the round",
                    modifier = modifier,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Table(columnHeaders: List<String>, rows: List<List<String>>) {
    val numColumns = columnHeaders.size
    val numRows = rows.size
    val borderColor = if(isSystemInDarkTheme()) Color.White else Color.Black

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)) { // Header row
        Row(modifier = Modifier.fillMaxWidth()) {
            repeat(numColumns) { columnIndex ->
                Text(
                        text = columnHeaders[columnIndex], modifier = Modifier
                    .weight(1f)
                    .border(
                            BorderStroke(0.5f.dp, borderColor)
                    )
                    .padding(6.dp), textAlign = TextAlign.Center
                )
            }
        } // Data rows
        repeat(numRows) { rowIndex ->
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(numColumns) { columnIndex ->
                    Text(
                            text = rows[rowIndex][columnIndex],
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                        BorderStroke(0.5f.dp, borderColor)
                                )
                                .padding(6.dp),
                            textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
