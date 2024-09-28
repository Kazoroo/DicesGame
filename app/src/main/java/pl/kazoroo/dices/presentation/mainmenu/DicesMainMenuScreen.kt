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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
                .width(dimensionResource(id = R.dimen.menu_button_width))
        ) {
            navController.navigate(Screen.GameScreen.withArgs())
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.dice_1), contentDescription = "Dice")
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.large_padding)))

        buttons.forEach { buttonInfo ->
            Button(
                onClick = buttonInfo.onClick,
                modifier = buttonInfo.modifier
            ) {
                Text(
                    text = buttonInfo.text
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                activity?.finish()
            },
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.menu_button_width))
                .padding(bottom = dimensionResource(id = R.dimen.large_padding))
        ) {
            Text(
                text = stringResource(R.string.exit)
            )
        }
    }
}