package pl.kazoroo.tavernFarkle.game.presentation.mainmenu.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import pl.kazoroo.tavernFarkle.R
import pl.kazoroo.tavernFarkle.ui.theme.DarkRed

@Composable
fun DialogHeader(
    headerText: String,
    onCloseClick: () -> Unit,
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
            text = headerText,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = onCloseClick,
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
}