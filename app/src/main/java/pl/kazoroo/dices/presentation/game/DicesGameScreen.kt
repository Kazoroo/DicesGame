package pl.kazoroo.dices.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.kazoroo.dices.R
import pl.kazoroo.dices.domain.model.SimpleData
import pl.kazoroo.dices.presentation.components.ButtonInfo
import pl.kazoroo.dices.presentation.game.components.Dices
import pl.kazoroo.dices.presentation.game.components.GameButtons
import pl.kazoroo.dices.presentation.game.components.PointsTable

@Composable
fun DicesGameScreen(viewModel: DicesViewModel) {
    val dataPlaceholder = listOf(
        SimpleData(
            pointsType = stringResource(R.string.total),
            yourPoints = "0",
            opponentPoints = "0"
        ),
        SimpleData(
            pointsType = stringResource(R.string.round),
            yourPoints = "0",
            opponentPoints = "0"
        ),
        SimpleData(
            pointsType = stringResource(R.string.selected),
            yourPoints = viewModel.pointsState.collectAsState().value.selectedPoints.toString(),
            opponentPoints = "0"
        ),
    )

    Box(
        modifier = Modifier.fillMaxSize()
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
                .height(220.dp)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PointsTable(data = dataPlaceholder)
            Dices(
                diceState = viewModel.diceState.collectAsState().value,
                diceOnClick = { index ->
                    viewModel.toggleDiceSelection(index)
                    viewModel.calculateScore()
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            val buttonsInfo = listOf(
                ButtonInfo(
                    text = stringResource(id = R.string.score_and_roll_again),
                    onClick = { viewModel.calculateScore() }
                ),
                ButtonInfo(
                    text = stringResource(id = R.string.pass),
                    onClick = { viewModel.calculateScore() }
                ),
            )
            GameButtons(buttonsInfo)
        }
    }
}


