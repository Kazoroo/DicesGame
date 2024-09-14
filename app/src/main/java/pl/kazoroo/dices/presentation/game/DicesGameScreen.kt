package pl.kazoroo.dices.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.kazoroo.dices.R
import pl.kazoroo.dices.domain.model.SimpleData
import pl.kazoroo.dices.presentation.game.components.Dices
import pl.kazoroo.dices.presentation.game.components.GameButtons
import pl.kazoroo.dices.presentation.game.components.PointsTable

@Composable
fun DicesGameScreen() {
    val dataPlaceholder = listOf(
        SimpleData("Total","300", "700"),
        SimpleData("Round", "100", "0"),
        SimpleData("Selected","200", "0"),
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
            Dices()
            Spacer(modifier = Modifier.weight(1f))
            GameButtons()
        }
    }
}


