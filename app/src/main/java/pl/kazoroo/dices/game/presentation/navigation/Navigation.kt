package pl.kazoroo.dices.game.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.kazoroo.dices.core.presentation.CoinsViewModel
import pl.kazoroo.dices.game.presentation.game.GameScreen
import pl.kazoroo.dices.game.presentation.mainmenu.MainMenuScreen
import pl.kazoroo.dices.shop.presentation.ShopScreen

@ExperimentalMaterial3Api
@Composable
fun Navigation(
    coinsViewModel: CoinsViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(
            route = Screen.MainScreen.route
        ) {
            MainMenuScreen(
                navController = navController,
                coinsViewModel = coinsViewModel
            )
        }
        composable(
            route = Screen.GameScreen.route
        ) {
            GameScreen(
                bettingActions = coinsViewModel,
                navController = navController
            )
        }
        composable(
            route = Screen.ShopScreen.route
        ) {
            ShopScreen(
                coinsViewModel = coinsViewModel
            )
        }
    }
}