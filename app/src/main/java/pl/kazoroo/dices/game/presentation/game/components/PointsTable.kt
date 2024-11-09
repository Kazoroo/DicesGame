package pl.kazoroo.dices.game.presentation.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.github.windedge.table.components.Divider
import pl.kazoroo.dices.R
import pl.kazoroo.dices.game.domain.model.TableData
import pl.kazoroo.dices.ui.theme.DarkGoldenBrown
import pl.kazoroo.dices.ui.theme.DarkRed
import pl.kazoroo.dices.ui.theme.HalfTransparentBlack

@Composable
fun PointsTable(
    data: List<TableData>,
    isOpponentTurn: Boolean
) {
    DataTable(
        columns = {
            column(contentAlignment = Alignment.Center) {
                Text("")
            }
            column(contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.you),
                    modifier = if(!isOpponentTurn) Modifier.bottomBorder(3.dp, DarkRed) else Modifier
                )
            }
            column(contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.opponent),
                    modifier = if(isOpponentTurn) Modifier.bottomBorder(3.dp, DarkRed) else Modifier
                )
            }
        },
        divider = { rowIndex ->
            if (rowIndex == 1 || rowIndex == 2) {
                Divider(
                    thickness = 2.dp,
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.Transparent,
                                    0.4f to DarkGoldenBrown,
                                    0.8f to HalfTransparentBlack,
                                    1.0f to Color.Transparent
                                )
                            )
                        ),
                    color = Color.Transparent
                )
            } else {
                Divider(
                    color = Color.Transparent
                )
            }
        },
        cellPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.medium_padding), vertical = dimensionResource(id = R.dimen.small_padding)),
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.medium_padding))
    ) {
        data.forEach { record ->
            row {
                cell {
                    Text(
                        text = record.pointsType,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(dimensionResource(R.dimen.table_cell_width))
                    )
                }
                cell(contentAlignment = Alignment.Center) {
                    Text(
                        text = record.yourPoints,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(dimensionResource(R.dimen.table_cell_width))
                    )
                }
                cell(contentAlignment = Alignment.Center) {
                    Text(
                        text = record.opponentPoints,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(dimensionResource(R.dimen.table_cell_width))
                    )
                }
            }
        }
    }
}