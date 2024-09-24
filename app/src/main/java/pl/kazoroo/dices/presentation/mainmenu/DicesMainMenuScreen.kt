package pl.kazoroo.dices.presentation.mainmenu

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pl.kazoroo.dices.R
import pl.kazoroo.dices.presentation.components.ButtonInfo
import pl.kazoroo.dices.presentation.navigation.Screen

@Composable
fun MainMenuScreen(navController: NavController) {
    val activity = (LocalContext.current as? Activity)
    val buttons = listOf(
        ButtonInfo(
            text = stringResource(R.string.play_with_ai),
            modifier = Modifier
                .width(200.dp)
                .semantics { contentDescription = "Play with AI button" }
        ) {
            navController.navigate(Screen.GameScreen.withArgs())
        },
        ButtonInfo(
            text = stringResource(R.string.exit),
            modifier = Modifier
                .width(200.dp)
                .semantics { contentDescription = "Exit button" }
        ) {
            activity?.finish()
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.semantics { contentDescription = "Main menu screen" }
    ) {
        Image(painter = painterResource(id = R.drawable.dice_1), contentDescription = "Dice")
        Spacer(modifier = Modifier.height(40.dp))

        buttons.forEach { buttonInfo ->
            Button(
                onClick = buttonInfo.onClick, modifier = buttonInfo.modifier
            ) {
                Text(
                    text = buttonInfo.text, fontSize = 16.sp
                )
            }
        }
    }
}