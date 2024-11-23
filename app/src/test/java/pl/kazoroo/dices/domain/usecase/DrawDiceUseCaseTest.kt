package pl.kazoroo.dices.domain.usecase

import org.junit.Test
import pl.kazoroo.dices.game.domain.usecase.DrawDiceUseCase

class DrawDiceUseCaseTest {

    @Test
    fun `check if there are 6 dice`() {
        val result = DrawDiceUseCase().invoke()

        assert(result.size == 6)
    }

    @Test
    fun `check if dice values are in appropriate range`() {
        val result = DrawDiceUseCase().invoke()

        val areValuesInRange = result.map { it.value.coerceIn(1..6) == it.value }

        assert(areValuesInRange.all { it })
    }
}