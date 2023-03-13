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
import pl.kazoroo.dices.ui.theme.dices.DicesViewModel
import pl.kazoroo.dices.ui.theme.dices.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DicesTheme {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                ) {
                    val viewModel: DicesViewModel = viewModel()
                    viewModel.queueEndBehavior()
                    MainScreen()
                }
            }
        }
    }
}