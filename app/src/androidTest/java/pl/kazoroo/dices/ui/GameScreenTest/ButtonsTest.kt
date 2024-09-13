package pl.kazoroo.dices.ui.GameScreenTest

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class ButtonsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun buttonEndQueueIsDisplayed() {
        composeTestRule.setContent {

        }

        composeTestRule.onNodeWithText("Confirm and end the queue").assertExists()
    }

    @Test
    fun buttonEndRoundIsDisplayed() {
        composeTestRule.setContent {

        }

        composeTestRule.onNodeWithText("Confirm and complete the round").assertExists()
    }
}