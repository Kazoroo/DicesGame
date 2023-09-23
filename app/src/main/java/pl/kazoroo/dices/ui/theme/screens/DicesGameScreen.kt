package pl.kazoroo.dices.ui.theme.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import pl.kazoroo.dices.navigation.Screen

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
 *
 * @return nothing
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun GameScreen(viewModel: DicesViewModel = viewModel(), navController: NavController) {
    val dice by viewModel.uiState.collectAsState()
    val buttonSize = calculateButtonsSize()

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        NavigationBar {
            items.forEachIndexed { _, item ->
                NavigationBarItem(selected = false, onClick = {
                    navController.navigate(Screen.MainScreen.withArgs())
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
                shouldntExist = dice.shouldntExist
        )
        Buttons(onQueueClick = { viewModel.queueEndBehavior() },
                onThrowClick = { viewModel.throwEndBehavior() },
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
fun SkuchaScreen(showSkucha: Boolean, sumOfPoints: Int) {
    @Composable
    fun GameResultScreen(text: String, backgroundColor: Color, fontColor: Color) {
        Box(modifier = Modifier.padding(bottom = 180.dp), contentAlignment = Alignment.Center) {
            Text(
                    text = text, fontFamily = FontFamily.Monospace, modifier = Modifier
                .background(
                        backgroundColor, RoundedCornerShape(8.dp)
                )
                .padding(7.dp), color = fontColor, fontSize = 85.sp
            )
        }
    }

    if (showSkucha) {
        if (sumOfPoints >= 2000) {
            GameResultScreen("Win!", Color(0x96000000), Color(0xff6fd633))
        }
        else {
            GameResultScreen("SKUCHA!", Color(0x96000000), Color.Red)
        }
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
                    painter = painterResource(id = dice[0]), contentDescription = "Dice", modifier = Modifier
                .padding(
                        2.dp
                )
                .size(if (!shouldntExist[0]) 110.dp else (-1).dp)
                .border(if (isSelected[0]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                    onClick.isSelectedBehavior(0)
                }
            )

            Image(
                    painter = painterResource(id = dice[1]), contentDescription = "Dice", modifier = Modifier
                .padding(
                        2.dp
                )
                .size(if (!shouldntExist[1]) 110.dp else (-1).dp)
                .border(if (isSelected[1]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                    onClick.isSelectedBehavior(1)
                }
            )

            Image(
                    painter = painterResource(id = dice[2]), contentDescription = "Dice", modifier = Modifier
                .padding(
                        2.dp
                )
                .size(if (!shouldntExist[2]) 110.dp else (-1).dp)
                .border(if (isSelected[2]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                    onClick.isSelectedBehavior(2)
                }
            )
        }

        Row {
            Image(
                    painter = painterResource(id = dice[3]), contentDescription = "Dice", modifier = Modifier
                .padding(
                        2.dp
                )
                .size(if (!shouldntExist[3]) 120.dp else (-1).dp)
                .border(if (isSelected[3]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                    onClick.isSelectedBehavior(3)
                }
            )

            Image(
                    painter = painterResource(id = dice[4]), contentDescription = "Dice", modifier = Modifier
                .padding(
                        2.dp
                )
                .size(if (!shouldntExist[4]) 120.dp else (-1).dp)
                .border(if (isSelected[4]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                    onClick.isSelectedBehavior(4)
                }
            )

            Image(
                    painter = painterResource(id = dice[5]), contentDescription = "Dice", modifier = Modifier
                .padding(
                        2.dp
                )
                .size(if (!shouldntExist[5]) 120.dp else (-1).dp)
                .border(if (isSelected[5]) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(4))
                .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                    onClick.isSelectedBehavior(5)
                }
            )
        }
    }
}

@Composable
fun Buttons(modifier: Modifier = Modifier,
            onThrowClick: () -> Unit,
            onQueueClick: () -> Unit,
            weight: Int,
            height: Int) {
    Row {
        OutlinedButton(
                onClick = onQueueClick,
                shape = RoundedCornerShape(15.dp),
                modifier = modifier
                    .height(height.dp)
                    .width(weight.dp) //.weight(1f)
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
                onClick = onThrowClick,
                shape = RoundedCornerShape(15.dp),
                modifier = modifier
                    .height(height.dp)
                    .width(weight.dp) //.weight(1f)
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

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)) { // Header row
        Row(modifier = Modifier.fillMaxWidth()) {
            repeat(numColumns) { columnIndex ->
                Text(
                        text = columnHeaders[columnIndex], modifier = Modifier
                    .weight(1f)
                    .border(
                            BorderStroke(0.5f.dp, Color.Black)
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
                                        BorderStroke(0.5f.dp, Color.Black)
                                )
                                .padding(6.dp),
                            textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
