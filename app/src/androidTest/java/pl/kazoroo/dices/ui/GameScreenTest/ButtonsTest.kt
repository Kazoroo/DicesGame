package pl.kazoroo.dices.ui.GameScreenTest

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import pl.kazoroo.dices.presentation.game.Buttons

class ButtonsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun buttonEndQueueIsDisplayed() {
        composeTestRule.setContent {
            Buttons(
                    onThrowClick = { },
                    onQueueClick = { },
                    weight = 2,
                    height = 2,
                    isDiceSelected = listOf(true, true, true, true, true, true)
            )
        }

        composeTestRule.onNodeWithText("Confirm and end the queue").assertExists()
    }

    @Test
    fun buttonEndRoundIsDisplayed() {
        composeTestRule.setContent {
            Buttons(
                    onThrowClick = { },
                    onQueueClick = { },
                    weight = 2,
                    height = 2,
                    isDiceSelected = listOf(true, true, true, true, true, true)
            )
        }

        composeTestRule.onNodeWithText("Confirm and complete the round").assertExists()
    }
}