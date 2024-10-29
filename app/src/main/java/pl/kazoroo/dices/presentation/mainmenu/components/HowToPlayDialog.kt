package pl.kazoroo.dices.presentation.mainmenu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import pl.kazoroo.dices.R
import pl.kazoroo.dices.ui.theme.DarkRed

@Composable
fun HowToPlayDialog(
    onClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onClick
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.medium_padding))
                )
        ) {
            Row(
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.small_padding),
                    start = dimensionResource(R.dimen.small_padding),
                    end = dimensionResource(R.dimen.small_padding),
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "How to play?",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(Modifier.weight(1f))

                IconButton(
                    onClick = onClick,
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Info icon",
                        tint = DarkRed,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Text(
                text = stringResource(R.string.how_to_play_long_tutorial),
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.medium_padding))
            )
        }
    }
}
