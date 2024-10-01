package pl.kazoroo.dices.domain.usecase

import org.junit.Test
import pl.kazoroo.dices.domain.model.Dice

class CheckForSkuchaUseCaseTest {

    @Test
    fun `check for skucha when all dice are visible and there are available points`() {
        val result = CheckForSkuchaUseCase().invoke(
            diceList = listOf(
                Dice(1, 0),
                Dice(2, 0),
                Dice(3, 0),
                Dice(4, 0),
                Dice(4, 0),
                Dice(6, 0)
            ),
            isDiceVisible = listOf(
                true,
                true,
                true,
                true,
                true,
                true,
            )
        )

        assert(!result)
    }

    @Test
    fun `check for skucha when all dice are visible and there are no available points`() {
        val result = CheckForSkuchaUseCase().invoke(
            diceList = listOf(
                Dice(6, 0),
                Dice(2, 0),
                Dice(3, 0),
                Dice(4, 0),
                Dice(4, 0),
                Dice(6, 0)
            ),
            isDiceVisible = listOf(
                true,
                true,
                true,
                true,
                true,
                true,
            )
        )

        assert(result)
    }

    @Test
    fun `check for skucha when three dice are visible and there are no available points`() {
        val result = CheckForSkuchaUseCase().invoke(
            diceList = listOf(
                Dice(6, 0),
                Dice(2, 0),
                Dice(3, 0),
                Dice(1, 0),
                Dice(1, 0),
                Dice(1, 0)
            ),
            isDiceVisible = listOf(
                true,
                true,
                true,
                false,
                false,
                false,
            )
        )

        assert(result)
    }

    @Test
    fun `check for skucha when all dice are invisible - should not be skucha`() {
        val result = CheckForSkuchaUseCase().invoke(
            diceList = listOf(
                Dice(6, 0),
                Dice(1, 0),
                Dice(3, 0),
                Dice(4, 0),
                Dice(4, 0),
                Dice(4, 0)
            ),
            isDiceVisible = listOf(
                false,
                false,
                false,
                false,
                false,
                false,
            )
        )

        assert(!result)
    }
}