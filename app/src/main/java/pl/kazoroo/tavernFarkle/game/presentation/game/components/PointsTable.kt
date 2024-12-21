package pl.kazoroo.tavernFarkle.game.presentation.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.github.windedge.table.components.Divider
import pl.kazoroo.tavernFarkle.R
import pl.kazoroo.tavernFarkle.game.domain.model.TableData
import pl.kazoroo.tavernFarkle.ui.theme.DarkGoldenBrown
import pl.kazoroo.tavernFarkle.ui.theme.DarkRed
import pl.kazoroo.tavernFarkle.ui.theme.HalfTransparentBlack

@Composable
fun PointsTable(
    data: List<TableData>,
    isOpponentTurn: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.old_paper_texture),
            contentDescription = "paper table background texture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
        )

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
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.large_padding))
        ) {
            data.forEach { record ->
                row {
                    cell {
                        Text(
                            text = record.pointsType,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(dimensionResource(R.dimen.table_cell_width))
                                .semantics { contentDescription = "points type ${record.pointsType}" }
                        )
                    }
                    cell(contentAlignment = Alignment.Center) {
                        Text(
                            text = record.yourPoints,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(dimensionResource(R.dimen.table_cell_width))
                                .semantics { contentDescription = "Number of your points ${record.yourPoints}" }
                        )
                    }
                    cell(contentAlignment = Alignment.Center) {
                        Text(
                            text = record.opponentPoints,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(dimensionResource(R.dimen.table_cell_width))
                                .semantics { contentDescription = "Number of your opponent points ${record.opponentPoints}" }
                        )
                    }
                }
            }
        }
    }
}