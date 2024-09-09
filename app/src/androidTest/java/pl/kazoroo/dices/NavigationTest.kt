package pl.kazoroo.dices

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.kazoroo.dices.presentation.game.DicesViewModel
import pl.kazoroo.dices.presentation.navigation.Navigation

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
            Navigation(viewModel = DicesViewModel())
        }
    }

    @Test
    fun navigation_verifyOverviewStartDestination() {
        composeTestRule.onNodeWithContentDescription("Main menu screen").assertIsDisplayed()
    }

    @Test
    fun navigation_clickPlayWithAI_navigatesToGameScreen() {
        composeTestRule.onNodeWithContentDescription("Play with AI button").performClick()

        composeTestRule.onNodeWithContentDescription("GameScreen").assertIsDisplayed()
    }

    @Test
    fun navigation_clickPlayWithPlayer_navigatesToGameScreen() {
        composeTestRule.onNodeWithContentDescription("Play with player button").performClick()

        composeTestRule.onNodeWithContentDescription("GameScreen").assertIsDisplayed()
    }

    @Test
    fun navigation_clickShop_navigatesToShopScreen() {
        composeTestRule.onNodeWithContentDescription("Shop button").performClick()

        composeTestRule.onNodeWithContentDescription("ShopScreen").assertIsDisplayed()
    }

    @Test
    fun navigation_clickSettings_navigatesToSettingsScreen() {
        composeTestRule.onNodeWithContentDescription("Settings button").performClick()

        composeTestRule.onNodeWithContentDescription("SettingsScreen").assertIsDisplayed()
    }

    @Test
    fun navigation_clickExit_exitFromApplication() {
        composeTestRule.onNodeWithContentDescription("Exit button").performClick()

        if (activity != null) {
            assert(activity!!.isFinishing)
        }
    }
}