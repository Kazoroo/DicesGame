package pl.kazoroo.dices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import pl.kazoroo.dices.data.DicesViewModel
import pl.kazoroo.dices.ui.theme.DicesTheme
import pl.kazoroo.dices.ui.theme.screens.GameScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DicesTheme {
                val viewModel by viewModels<DicesViewModel>()
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) { //viewModel.roundEndBehavior()
                    GameScreen(
                            viewModel = viewModel, navController = rememberNavController()
                    ) //Navigation()
                }
            }
        }
    }
}