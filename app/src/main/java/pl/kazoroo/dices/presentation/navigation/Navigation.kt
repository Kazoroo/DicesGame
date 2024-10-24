package pl.kazoroo.dices.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.kazoroo.dices.presentation.game.DicesGameScreen
import pl.kazoroo.dices.presentation.game.DicesViewModel
import pl.kazoroo.dices.presentation.mainmenu.MainMenuScreen

@ExperimentalMaterial3Api
@Composable
fun Navigation(viewModel: DicesViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(
            route = Screen.MainScreen.route
        ) {
            MainMenuScreen(navController = navController)
        }
        composable(
            route = Screen.GameScreen.route
        ) {
            DicesGameScreen(viewModel, navController)
        }
    }
}