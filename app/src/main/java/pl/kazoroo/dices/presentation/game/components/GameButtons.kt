package pl.kazoroo.dices.presentation.game.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameButtons(
    modifier: Modifier = Modifier,
        onThrowClick: () -> Unit,
        onQueueClick: () -> Unit,
        isDiceSelected: List<Boolean>) {
    Row {
        OutlinedButton(
            onClick = onQueueClick,
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .padding(
                    start = 5.dp,
                    end = 5.dp
                )
        ) {
            Text(
                text = "Confirm and end the queue",
                modifier = modifier,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = onThrowClick,
            enabled = isDiceSelected.count { !it } != 6,
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .padding(
                    start = 5.dp,
                    end = 5.dp
                )
        ) {
            Text(
                text = "Confirm and complete the throw",
                modifier = modifier,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}