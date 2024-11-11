package pl.kazoroo.dices.game.presentation.mainmenu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import pl.kazoroo.dices.R

@Composable
fun BettingDialog(
    onClick: () -> Unit,
    onCloseClick: () -> Unit,
) {
    var betAmount by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onCloseClick
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.medium_padding))
                )
        ) {
            DialogHeader(
                headerText = stringResource(R.string.betting)
            ) {
                onCloseClick()
            }

            Row {
                Text(
                    text = stringResource(R.string.place_a_bet),
                )

                TextField(
                    value = betAmount,
                    onValueChange = { betAmount = it },
                    singleLine = true,
                    modifier = Modifier.width(50.dp)
                )

                Icon(
                    painter = painterResource(R.drawable.coin), //FIXME: it can't be svg
                    contentDescription = "Coin icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
