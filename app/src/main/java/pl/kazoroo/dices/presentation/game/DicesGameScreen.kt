package pl.kazoroo.dices.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import pl.kazoroo.dices.R

@Composable
fun DicesGameScreen() {
    data class SimpleData(val pointsType: String, val yourPoints: String, val opponentPoints: String)

    val dataPlaceholder = listOf(
        SimpleData("Total","300", "700"),
        SimpleData("Round", "100", "0"),
        SimpleData("Selected","200", "0"),
    )

        Box(
            modifier = Modifier
                .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.wooden_background_texture),
            contentDescription = "Wooden background texture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.paper_texture),
            contentDescription = "Wooden background texture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
        )

        DataTable(
            columns = {
                column(contentAlignment = Alignment.Center) {
                    Text("")
                }
                column(contentAlignment = Alignment.Center) {
                    Text("You")
                }
                column(contentAlignment = Alignment.CenterEnd) {
                    Text("Opponent")
                }
            },
            divider = { Text(text = ".") },
            cellPadding = PaddingValues(horizontal = 50.dp, vertical = 6.dp),
            modifier = Modifier.padding(top = 18.dp)
        ) {
            dataPlaceholder.forEach { record ->
                row {
                    cell {
                        Text(
                            text = record.pointsType,
                            textAlign = TextAlign.Center
                        )
                    }
                    cell(contentAlignment = Alignment.Center) {
                        Text(
                            text = record.yourPoints,
                            textAlign = TextAlign.Center
                        )
                    }
                    cell(contentAlignment = Alignment.Center) {
                        Text(
                            text = record.opponentPoints,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


