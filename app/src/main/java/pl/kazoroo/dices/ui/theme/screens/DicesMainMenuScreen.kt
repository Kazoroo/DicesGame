package pl.kazoroo.dices.ui.theme.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.kazoroo.dices.R
import pl.kazoroo.dices.data.DicesViewModel
import pl.kazoroo.dices.navigation.Screen

data class ButtonInfo(val text: String, val onClick: () -> Unit)

@Composable
fun MainMenuScreen(navController: NavController, viewModel: DicesViewModel = viewModel()) {
    val activity = (LocalContext.current as? Activity)
    val buttons =
        listOf(ButtonInfo(text = "Play with AI") { navController.navigate(Screen.GameScreen.withArgs()) },
                ButtonInfo(text = "Play with player") { navController.navigate(Screen.GameScreen.withArgs()) },
                ButtonInfo(text = "Shop") { navController.navigate(Screen.ShopScreen.withArgs()) },
                ButtonInfo(text = "Settings") { navController.navigate(Screen.SettingsScreen.withArgs()) },
                ButtonInfo(text = "Exit") { activity?.finish() })

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.dice_1), contentDescription = "Dice")
        Spacer(modifier = Modifier.height(40.dp))

        buttons.forEach { buttonInfo ->
            Button(
                    onClick = buttonInfo.onClick, modifier = Modifier.width(200.dp)
            ) {
                Text(
                        text = buttonInfo.text, fontSize = 16.sp
                )
            }
        }
    }
}