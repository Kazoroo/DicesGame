package pl.kazoroo.dices.game.presentation.navigation

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main_screen")
    data object GameScreen : Screen("game_screen")
    data object ShopScreen : Screen("shop_screen")

    /**
     * Creates a string route including arguments
     * @param args arguments that will be in the string route
     * @return string that is a route to the screen
     */
    fun withArgs(vararg args: Array<Int>): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
