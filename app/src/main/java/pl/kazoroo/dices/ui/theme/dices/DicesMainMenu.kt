package pl.kazoroo.dices.ui.theme.dices

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.kazoroo.dices.R

@Composable
fun MainMenu() { 
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.dice_1), contentDescription = "Dice")

        Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.width(200.dp)


        ) {
            Text(text = "Play with AI")
        }

        Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Play with player")
        }

        Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Skins")
        }

        Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Options")
        }

        Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Exit")
        }
    }
}