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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import pl.kazoroo.dices.R
import pl.kazoroo.dices.domain.model.TableData
import pl.kazoroo.dices.presentation.components.ButtonInfo
import pl.kazoroo.dices.presentation.game.components.Dices
import pl.kazoroo.dices.presentation.game.components.GameButtons
import pl.kazoroo.dices.presentation.game.components.PointsTable

@Composable
fun DicesGameScreen(viewModel: DicesViewModel) {
    val tableData = listOf(
        TableData(
            pointsType = stringResource(R.string.total),
            yourPoints = viewModel.pointsState.collectAsState().value.totalPoints.toString(),
            opponentPoints = "0"
        ),
        TableData(
            pointsType = stringResource(R.string.round),
            yourPoints = viewModel.pointsState.collectAsState().value.roundPoints.toString(),
            opponentPoints = "0"
        ),
        TableData(
            pointsType = stringResource(R.string.selected),
            yourPoints = viewModel.pointsState.collectAsState().value.selectedPoints.toString(),
            opponentPoints = "0"
        ),
    )
    val isSkucha = viewModel.skuchaState.collectAsState().value

    Box(
        modifier = Modifier.fillMaxSize(),
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
                .height(dimensionResource(id = R.dimen.menu_button_width))
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PointsTable(data = tableData)
            Dices(
                diceState = viewModel.diceState.collectAsState().value,
                diceOnClick = { index ->
                    if(!viewModel.skuchaState.value) {
                        viewModel.toggleDiceSelection(index)
                        viewModel.calculateScore()
                    }
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            val buttonsInfo = listOf(
                ButtonInfo(
                    text = stringResource(id = R.string.score_and_roll_again),
                    onClick = {
                        viewModel.countPoints()
                        viewModel.checkForSkucha()
                    }
                ),
                ButtonInfo(
                    text = stringResource(id = R.string.pass),
                    onClick = {
                        viewModel.passTheRound()
                    }
                ),
            )
            GameButtons(
                buttonsInfo,
                isSkucha
            )
        }

        var showTextWithDelay by remember { mutableStateOf(false) }

        LaunchedEffect(isSkucha) {
            showTextWithDelay = isSkucha
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if(showTextWithDelay) {
                Text(
                    text = "Skucha!",
                    color = Color(212, 212, 212),
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
    }
}


