package pl.kazoroo.dices.presentation.mainmenu

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
                .width(220.dp)
        ) {
            navController.navigate(Screen.GameScreen.withArgs())
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.dice_1), contentDescription = "Dice")
        Spacer(modifier = Modifier.height(40.dp))

        buttons.forEach { buttonInfo ->
            Button(
                onClick = buttonInfo.onClick,
                modifier = buttonInfo.modifier
            ) {
                Text(
                    text = buttonInfo.text, fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                activity?.finish()
            },
            modifier = Modifier
                .width(220.dp)
                .padding(bottom = 50.dp)
        ) {
            Text(
                text = stringResource(R.string.exit), fontSize = 18.sp
            )
        }
    }
}