package pl.kazoroo.dices.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.kazoroo.dices.domain.model.Dice

open class CalculatePointsUseCaseTest {
    @Test
    fun `check if three 1s gives 1000 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(1, 0),
                Dice(1, 0),
                Dice(1, 0),
                Dice(2, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                false,
                false,
                false,
            )
        )

        assertEquals(1000, result)
    }

    @Test
    fun `check if game count not selected dices`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(1, 0),
                Dice(1, 0),
                Dice(1, 0),
                Dice(2, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                false,
                false,
                false,
                true,
                true,
                true,
            )
        )

        assertEquals(0, result)
    }

    @Test
    fun `check if four 1s gives 2000 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(1, 0),
                Dice(1, 0),
                Dice(1, 0),
                Dice(1, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                true,
                false,
                false,
            )
        )

        assertEquals(2000, result)
    }

    @Test
    fun `check if four 5s gives 1000 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(5, 0),
                Dice(5, 0),
                Dice(5, 0),
                Dice(5, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                true,
                false,
                false,
            )
        )

        assertEquals(1000, result)
    }

    @Test
    fun `check if six 5s gives 2000 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(5, 0),
                Dice(5, 0),
                Dice(5, 0),
                Dice(5, 0),
                Dice(5, 0),
                Dice(5, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                true,
                true,
                true,
            )
        )

        assertEquals(2000, result)
    }

    @Test
    fun `check if four 3s gives 600 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(3, 0),
                Dice(3, 0),
                Dice(3, 0),
                Dice(1, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                false,
                true,
                false,
            )
        )

        assertEquals(600, result)
    }

    @Test
    fun `check if five 3s gives 900 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(3, 0),
                Dice(3, 0),
                Dice(3, 0),
                Dice(3, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                true,
                true,
                false,
            )
        )

        assertEquals(900, result)
    }

    @Test
    fun `check if one 5 gives 50 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(5, 0),
                Dice(3, 0),
                Dice(3, 0),
                Dice(1, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                false,
                false,
                false,
                false,
                false,
            )
        )

        assertEquals(50, result)
    }

    @Test
    fun `check if two 5 gives 100 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(5, 0),
                Dice(5, 0),
                Dice(3, 0),
                Dice(1, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                false,
                false,
                false,
                false,
            )
        )

        assertEquals(100, result)
    }

    @Test
    fun `check if straight 1 to 5 gives 500 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(1, 0),
                Dice(2, 0),
                Dice(5, 0),
                Dice(4, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                true,
                true,
                false,
            )
        )

        assertEquals(500, result)
    }

    @Test
    fun `check if straight 1 to 5 plus 1 gives 600 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(1, 0),
                Dice(2, 0),
                Dice(5, 0),
                Dice(4, 0),
                Dice(3, 0),
                Dice(1, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                true,
                true,
                true,
            )
        )

        assertEquals(600, result)
    }

    @Test
    fun `check if straight 2 to 6 gives 750 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(1, 0),
                Dice(6, 0),
                Dice(5, 0),
                Dice(4, 0),
                Dice(2, 0),
                Dice(3, 0),
            ),
            isDiceSelected = listOf(
                false,
                true,
                true,
                true,
                true,
                true,
            )
        )

        assertEquals(750, result)
    }

    @Test
    fun `check if straight 1 to 6 gives 1500 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(1, 0),
                Dice(2, 0),
                Dice(3, 0),
                Dice(4, 0),
                Dice(5, 0),
                Dice(6, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                true,
                true,
                true,
            )
        )

        assertEquals(1500, result)
    }

    @Test
    fun `check if straight 1 to 5 plus mismatched dice gives 0 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(1, 0),
                Dice(2, 0),
                Dice(3, 0),
                Dice(4, 0),
                Dice(5, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                true,
                true,
                true,
            )
        )

        assertEquals(0, result)
    }

    @Test
    fun `check if two 5 and mismatched dice gives 0 points`() {
        val result = CalculatePointsUseCase().invoke(
            diceList = listOf(
                Dice(5, 0),
                Dice(5, 0),
                Dice(3, 0),
                Dice(1, 0),
                Dice(3, 0),
                Dice(4, 0),
            ),
            isDiceSelected = listOf(
                true,
                true,
                true,
                false,
                false,
                false,
            )
        )

        assertEquals(0, result)
    }
}