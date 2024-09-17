package pl.kazoroo.dices.presentation.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.kazoroo.dices.R
import pl.kazoroo.dices.presentation.components.ButtonInfo

@Composable
fun GameButtons(buttonsInfo: List<ButtonInfo>) {
    val roundingPercentage = 25

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        buttonsInfo.forEach { buttonInfo ->
            Button(
                onClick = buttonInfo.onClick,
                shape = RoundedCornerShape(roundingPercentage),
                modifier = Modifier
                    .weight(0.5f)
                    .height(230.dp)
                    .padding(horizontal = 10.dp, vertical = 50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.paper_texture),
                        contentDescription = stringResource(R.string.score_and_roll_again),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(roundingPercentage))
                    )

                    Text(
                        text = buttonInfo.text,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            }
        }
    }
}