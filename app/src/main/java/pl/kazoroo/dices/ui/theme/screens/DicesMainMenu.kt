package pl.kazoroo.dices.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.kazoroo.dices.R
import pl.kazoroo.dices.navigation.Screen

@Composable
fun MainMenu(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.dice_1), contentDescription = "Dice")

        Button(
                onClick = { navController.navigate(Screen.GameScreen.withArgs()) },
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
                onClick = { navController.navigate(Screen.SettingsScreen.withArgs()) },
                modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Settings")
        }

        Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Exit")
        }
    }
}