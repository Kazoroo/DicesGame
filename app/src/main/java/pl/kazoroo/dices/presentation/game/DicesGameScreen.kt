package pl.kazoroo.dices.presentation.game

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pl.kazoroo.dices.presentation.navigation.items

@Composable
fun calculateButtonsSize(): Array<Int> {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val buttonWidth = screenWidth / 2

    val buttonHeight = buttonWidth / 1.61803398875

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
fun GameScreen(viewModel: DicesViewModel, navController: NavController) {
    val buttonSize = calculateButtonsSize()
    val isShowingExitDialog = remember { mutableStateOf(false) }

    if (isShowingExitDialog.value) {
        AlertDialog(
            onDismissRequest = { isShowingExitDialog.value = false },
            confirmButton = {
                TextButton(
                        onClick = {
                            isShowingExitDialog.value = false
                            navController.navigateUp()
                            viewModel.resetState()
                        },
                        modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(
                        onClick = { isShowingExitDialog.value = false },
                        modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "No")
                }
            },
            text = {
                Text(text = "Do you really want to leave a match? It will take 30 coins.")
            },
            title = {
                Text(text = "Leaving a match")
            }
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        NavigationBar {
            items.forEachIndexed { _, item ->
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        isShowingExitDialog.value = true
                    },
                    label = {
                        Text(text = item.title)
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon, contentDescription = item.title
                        )
                    }
                )
            }
        }
    }) {

    }

    Column(modifier = Modifier.semantics { contentDescription = "GameScreen" }) {
        Table(
                columnHeaders = listOf("Points", "You", "Opponent"), rows = listOf(
                listOf("Sum:          ", "${viewModel.sumOfPoints}/2000", ""),
                listOf("    In this throw:", "${viewModel.throwPoints}", ""),
                listOf("  Selected:     ", "${viewModel.points}", "")
        )
        )
        Dices(viewModel = viewModel)
        Buttons(onQueueClick = { viewModel.queueEndBehavior() },
                onThrowClick = { viewModel.throwEndBehaviour() },
                weight = buttonSize[0],
                height = buttonSize[1],
                isDiceSelected = viewModel.isDiceSelected
        )
    }

    if (viewModel.gameEnd) {
        SkuchaScreen(
                showSkucha = viewModel.showSkucha,
                sumOfPoints = viewModel.sumOfPoints
        )
        viewModel.showSkuchaBehavior()
    }
}

@Composable
fun SkuchaScreen(showSkucha: Boolean, sumOfPoints: Int) {
    if (showSkucha) {
        if (sumOfPoints >= 2000) {
            GameResultScreen("You win!", Color(0xff6fd633))
        }
        else {
            GameResultScreen("SKUCHA!", Color.Red)
        }
    }
}

@Composable
fun GameResultScreen(text: String, fontColor: Color) {
    Box(modifier = Modifier.padding(bottom = 180.dp), contentAlignment = Alignment.Center) {
        Text(
                text = text,
                color = fontColor,
                fontSize = 80.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .background(Color(0x96000000), RoundedCornerShape(8.dp))
                    .padding(7.dp),
        )
    }
}

data class DicesInfo(val dice: Int,
                     val isSelected: Boolean,
                     val onClick: () -> Unit,
                     val shouldDiceExist: Boolean)

@Composable
fun Dices(viewModel: DicesViewModel) {
    val dicesRows = List(2) { rowIndex ->
        List(3) { columnIndex ->
            val index = rowIndex * 3 + columnIndex
            DicesInfo(
                    dice = viewModel.dicesList[index],
                    isSelected = viewModel.isDiceSelected[index],
                    onClick = { viewModel.isSelectedBehavior(index) },
                    shouldDiceExist = viewModel.shouldDiceExist[index]
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
                                .size(if (info.shouldDiceExist) 110.dp else (-1).dp)
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
            onThrowClick: () -> Unit,
            onQueueClick: () -> Unit,
            weight: Int,
            height: Int,
            isDiceSelected: List<Boolean>) {
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
        Log.i("dices",
                "isDiceSelected.count { false } == 6 - ${isDiceSelected.count { false } == 6}")
        Log.i("dices", "isDiceSelected - $isDiceSelected")
        Button(onClick = onThrowClick,
                enabled = isDiceSelected.count { !it } != 6,
                shape = RoundedCornerShape(15.dp),
                modifier = modifier
                    .height(height.dp)
                    .width(weight.dp)
                    .padding(start = 5.dp, end = 5.dp)) {
            Text(
                    text = "Confirm and complete the throw",
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
                        text = columnHeaders[columnIndex],
                        modifier = Modifier
                            .weight(1f)
                            .border(BorderStroke(0.5f.dp, borderColor))
                            .padding(6.dp),
                        textAlign = TextAlign.Center
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
                                .border(BorderStroke(0.5f.dp, borderColor))
                                .padding(6.dp),
                            textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
