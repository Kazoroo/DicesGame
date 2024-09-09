package pl.kazoroo.dices.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.kazoroo.dices.presentation.game.DicesViewModel
import pl.kazoroo.dices.presentation.game.GameScreen
import pl.kazoroo.dices.presentation.mainmenu.MainMenuScreen
import pl.kazoroo.dices.presentation.settings.SettingsScreen

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
            route = Screen.SettingsScreen.route
        ) {
            SettingsScreen(navController = navController)
        }
        composable(
            route = Screen.GameScreen.route
        ) { entry ->
            GameScreen(
                    navController = navController, viewModel = viewModel
            )
        }
    }
}