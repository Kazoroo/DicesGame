package pl.kazoroo.dices.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso
import org.junit.Before
import org.junit.Rule
import org.junit.Test
<<<<<<< HEAD
import pl.kazoroo.dices.game.presentation.game.GameViewModel
import pl.kazoroo.dices.game.presentation.navigation.Navigation
=======
>>>>>>> coinsAndBets

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController
    private var activity: ComponentActivity? = null

    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setup() {
        composeTestRule.setContent {
            activity = (LocalContext.current as? ComponentActivity)
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                    ComposeNavigator()
            )
<<<<<<< HEAD
            Navigation(gameViewModel = GameViewModel())
=======
>>>>>>> coinsAndBets
        }
    }

    @Test
    fun navigation_verifyOverviewStartDestination() {
        composeTestRule.onNodeWithTag("Main menu screen").assertIsDisplayed()
    }

    @Test
    fun navigation_clickPlayWithPlayer_navigatesToGameScreen() {
        composeTestRule.onNodeWithTag("Play with AI button").performClick()

        composeTestRule.onNodeWithTag("Game screen").assertIsDisplayed()
    }

    @Test
    fun navigation_clickExit_exitFromApplication() {
        composeTestRule.onNodeWithTag("Exit button").performClick()

        if (activity != null) {
            assert(activity!!.isFinishing)
        }
    }

    @Test
    fun navigation_clickDeviceBackButton_navigatesToMainMenuScreen() {
        composeTestRule.onNodeWithTag("Play with AI button").performClick()
        Espresso.pressBack()
        composeTestRule.onNodeWithTag("Main menu screen").assertIsDisplayed()
    }
}