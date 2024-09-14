package pl.kazoroo.dices.presentation.game.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import pl.kazoroo.dices.domain.model.SimpleData

@Composable
fun PointsTable(data: List<SimpleData>) {
    DataTable(
        columns = {
            column(contentAlignment = Alignment.Center) {
                Text("")
            }
            column(contentAlignment = Alignment.Center) {
                Text("You")
            }
            column(contentAlignment = Alignment.Center) {
                Text("Opponent")
            }
        },
        divider = { Text(text = ".") },
        cellPadding = PaddingValues(horizontal = 20.dp),
        modifier = Modifier.padding(top = 18.dp)
    ) {
        data.forEach { record ->
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