package pl.kazoroo.dices.presentation.mainmenu

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import pl.kazoroo.dices.R
import pl.kazoroo.dices.presentation.components.ButtonInfo
import pl.kazoroo.dices.presentation.components.DiceButton
import pl.kazoroo.dices.presentation.navigation.Screen

@Composable
fun MainMenuScreen(navController: NavController) {
    val activity = (LocalContext.current as? Activity)
    val buttonsModifier: Modifier = Modifier
        .height(dimensionResource(R.dimen.menu_button_height))
        .width(dimensionResource(R.dimen.menu_button_width))

    val buttons = listOf(
        ButtonInfo(
            text = stringResource(R.string.play_with_ai),
            modifier = buttonsModifier
                .testTag("Play with AI button")
        ) {
            navController.navigate(Screen.GameScreen.withArgs())
        }
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.wooden_background_texture),
            contentDescription = "Wooden background texture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.testTag("Main menu screen")
        ) {
            Image(painter = painterResource(id = R.drawable.dice_1), contentDescription = "Dice")

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Yellow
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.large_padding)))

            buttons.forEach { buttonInfo ->
                DiceButton(
                    buttonInfo = buttonInfo,
                    modifier = buttonsModifier
                )
            }

            Spacer(modifier = Modifier.weight(0.9f))

            DiceButton(
                buttonInfo = ButtonInfo(
                    text = stringResource(R.string.exit),
                    onClick = { activity?.finish() }
                ),
                modifier = buttonsModifier
                    .testTag("Exit button")
            )

            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}