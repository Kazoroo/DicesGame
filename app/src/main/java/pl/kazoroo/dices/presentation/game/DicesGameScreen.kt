package pl.kazoroo.dices.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import pl.kazoroo.dices.R
import pl.kazoroo.dices.domain.model.TableData
import pl.kazoroo.dices.presentation.components.ButtonInfo
import pl.kazoroo.dices.presentation.game.components.Dices
import pl.kazoroo.dices.presentation.game.components.GameButtons
import pl.kazoroo.dices.presentation.game.components.PointsTable
import pl.kazoroo.dices.presentation.sound.SoundPlayer
import pl.kazoroo.dices.presentation.sound.SoundType
import pl.kazoroo.dices.ui.theme.DarkRed

@Composable
fun DicesGameScreen(
    viewModel: DicesViewModel,
    navController: NavHostController
) {
    val isSkucha = viewModel.skuchaState.collectAsState().value
    val isGameEnd = viewModel.isGameEnd.collectAsState().value
    val isOpponentTurn = viewModel.isOpponentTurn.collectAsState().value
    val selectedPoints = viewModel.userPointsState.collectAsState().value.selectedPoints
    val tableData = listOf(
        TableData(
            pointsType = stringResource(R.string.total),
            yourPoints = viewModel.userPointsState.collectAsState().value.totalPoints.toString(),
            opponentPoints = viewModel.opponentPointsState.collectAsState().value.totalPoints.toString()
        ),
        TableData(
            pointsType = stringResource(R.string.round),
            yourPoints = viewModel.userPointsState.collectAsState().value.roundPoints.toString(),
            opponentPoints = viewModel.opponentPointsState.collectAsState().value.roundPoints.toString()
        ),
        TableData(
            pointsType = stringResource(R.string.selected_forDices),
            yourPoints = selectedPoints.toString(),
            opponentPoints = viewModel.opponentPointsState.collectAsState().value.selectedPoints.toString()
        ),
    )

    LaunchedEffect(true) {
        viewModel.checkForSkucha(navController)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("Game screen")
    ) {
        Image(
            painter = painterResource(id = R.drawable.wooden_background_texture),
            contentDescription = "Wooden background texture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.old_paper_texture),
            contentDescription = "paper table background texture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.desks_size))
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PointsTable(
                data = tableData,
                isOpponentTurn = isOpponentTurn
            )
            Dices(
                diceState = viewModel.diceState.collectAsState().value,
                diceOnClick = { index ->
                    if(!viewModel.skuchaState.value) {
                        viewModel.toggleDiceSelection(index)
                    }
                },
                isDiceClickable = !isOpponentTurn && !isGameEnd
            )
            Spacer(modifier = Modifier.weight(1f))

            val buttonsInfo = listOf(
                ButtonInfo(
                    text = stringResource(id = R.string.score_and_roll_again),
                    onClick = {
                        if(!isSkucha) {
                            SoundPlayer.playSound(SoundType.DICE_ROLLING)
                            viewModel.prepareForNextThrow()
                            viewModel.checkForSkucha(navController)
                        } else { Unit }
                    },
                    enabled = (selectedPoints != 0 && !isOpponentTurn) && !isGameEnd
                ),
                ButtonInfo(
                    text = stringResource(id = R.string.pass),
                    onClick = {
                        if(!isSkucha) {
                            SoundPlayer.playSound(SoundType.DICE_ROLLING)
                            viewModel.passTheRound(navController)
                        } else { Unit }
                    },
                    enabled = (selectedPoints != 0 && !isOpponentTurn) && !isGameEnd
                ),
            )

            GameButtons(
                buttonsInfo = buttonsInfo,
            )
        }

        @Composable
        fun gameResultAndSkuchaDialog(text: String, textColor: Color) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = textColor,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .background(
                            color = Color(26, 26, 26, 220),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
                        )
                        .padding(dimensionResource(id = R.dimen.medium_padding))
                )
            }
        }

        var isSkuchaDialogVisible by remember { mutableStateOf(false) }
        var isGameResultDialogVisible by remember { mutableStateOf(false) }

        LaunchedEffect(isSkucha) {
            isSkuchaDialogVisible = isSkucha
        }

        LaunchedEffect(isGameEnd) {
            delay(1000L)

            isGameResultDialogVisible = isGameEnd
        }

        if(isSkuchaDialogVisible) {
            gameResultAndSkuchaDialog(text = "Skucha!", textColor = Color(212, 212, 212))
        }

        if(isGameResultDialogVisible && isOpponentTurn) {
            gameResultAndSkuchaDialog(text = "Defeat", textColor = DarkRed)
        } else if(isGameResultDialogVisible) {
            gameResultAndSkuchaDialog(text = "Win!", textColor = Color.Green)
        }
    }
}


