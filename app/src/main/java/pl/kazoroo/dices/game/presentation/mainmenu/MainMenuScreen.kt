package pl.kazoroo.dices.game.presentation.mainmenu

import android.app.Activity
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import pl.kazoroo.dices.R
import pl.kazoroo.dices.core.data.presentation.BettingViewModel
import pl.kazoroo.dices.game.presentation.components.ButtonInfo
import pl.kazoroo.dices.game.presentation.components.DiceButton
import pl.kazoroo.dices.game.presentation.mainmenu.components.BettingDialog
import pl.kazoroo.dices.game.presentation.mainmenu.components.HowToPlayDialog
import pl.kazoroo.dices.game.presentation.navigation.Screen
import pl.kazoroo.dices.game.presentation.sound.SoundPlayer
import pl.kazoroo.dices.game.presentation.sound.SoundType

@Composable
fun MainMenuScreen(navController: NavController, bettingViewModel: BettingViewModel) {
    val activity = (LocalContext.current as? Activity)
    val buttonsModifier: Modifier = Modifier
        .height(dimensionResource(R.dimen.menu_button_height))
        .width(dimensionResource(R.dimen.menu_button_width))
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val imageSize = (screenWidth / 1.5f)
    var isHelpDialogVisible by remember { mutableStateOf(false) }
    var isBettingDialogVisible by remember { mutableStateOf(false) }
    val buttons = listOf(
        ButtonInfo(
            text = stringResource(R.string.play_with_computer),
            modifier = buttonsModifier
                .testTag("Play with AI button")
        ) {
            isBettingDialogVisible = true
        }
    )

    if(isHelpDialogVisible) {
        HowToPlayDialog(
            onCloseClick = { isHelpDialogVisible = false }
        )
    }

    if(isBettingDialogVisible) {
        BettingDialog(
            onCloseClick = {
                isBettingDialogVisible = false
            },
            onClick = {
                SoundPlayer.playSound(SoundType.CLICK)
                navController.navigate(Screen.GameScreen.withArgs())
            },
            bettingViewModel = bettingViewModel
        )
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

        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = dimensionResource(R.dimen.small_padding),
                    start = dimensionResource(R.dimen.small_padding)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = bettingViewModel.coinsAmount.collectAsState().value,
                color = Color.White
            )

            Image(
                painter = painterResource(R.drawable.coin),
                contentDescription = "Coin icon",
                modifier = Modifier
                    .size(dimensionResource(R.dimen.coin_icon_size))
                    .padding(start = dimensionResource(R.dimen.small_padding))
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
                    onClick = {
                        activity?.finish()
                        SoundPlayer.playSound(SoundType.CLICK)
                    }
                ),
                modifier = buttonsModifier
                    .testTag("Exit button")
            )

            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}