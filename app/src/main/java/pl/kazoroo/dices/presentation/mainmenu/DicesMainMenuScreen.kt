package pl.kazoroo.dices.presentation.mainmenu

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import pl.kazoroo.dices.R
import pl.kazoroo.dices.presentation.components.ButtonInfo
import pl.kazoroo.dices.presentation.components.DiceButton
import pl.kazoroo.dices.presentation.navigation.Screen
import pl.kazoroo.dices.ui.theme.DarkRed

@Composable
fun MainMenuScreen(navController: NavController) {
    val activity = (LocalContext.current as? Activity)
    val buttonsModifier: Modifier = Modifier
        .height(dimensionResource(R.dimen.menu_button_height))
        .width(dimensionResource(R.dimen.menu_button_width))

    val buttons = listOf(
        ButtonInfo(
            text = stringResource(R.string.play_with_computer),
            modifier = buttonsModifier
                .testTag("Play with AI button")
        ) {
            navController.navigate(Screen.GameScreen.withArgs())
        }
    )

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val imageSize = (screenWidth / 1.5f)
    var isHelpDialogVisible by remember { mutableStateOf(false) }

    if(isHelpDialogVisible) {
        Dialog(
            onDismissRequest = { isHelpDialogVisible = false }
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(dimensionResource(R.dimen.medium_padding))
                    )
            ) {
                Row {
                    Text(
                        text = "How to play?",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
                    )

                    Spacer(Modifier.weight(1f))

                    IconButton(
                        onClick = { isHelpDialogVisible = false },
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.medium_padding))
                            .size(dimensionResource(R.dimen.icon_button_size))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Info icon",
                            tint = DarkRed,
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_button_size))
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

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.wooden_background_texture),
                contentDescription = "Wooden background texture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = { isHelpDialogVisible = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = dimensionResource(R.dimen.small_padding),
                        end = dimensionResource(R.dimen.small_padding)
                    )
                    .size(dimensionResource(R.dimen.icon_button_size))
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info icon",
                    tint = Color.White,
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_button_size))
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.testTag("Main menu screen")
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dice_1),
                    contentDescription = "Dice",
                    modifier = Modifier.size(imageSize)
                )

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