package pl.kazoroo.dices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.kazoroo.dices.ui.theme.DicesTheme
import pl.kazoroo.dices.ui.theme.dices.data.DicesViewModel
import pl.kazoroo.dices.ui.theme.dices.MainMenu
import pl.kazoroo.dices.ui.theme.dices.Settings
import pl.kazoroo.dices.ui.theme.dices.calculateButtonsSize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DicesTheme {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                ) {
                    val buttonsSize = calculateButtonsSize()
                    val viewModel: DicesViewModel = viewModel()
                    viewModel.throwEndBehavior()
                    //GameScreen(buttonsSize = buttonsSize)
                    Settings()
                }
            }
        }
    }
}