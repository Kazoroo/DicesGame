package pl.kazoroo.dices.domain.usecase

import pl.kazoroo.dices.R
import kotlin.random.Random

class DrawDiceUseCase {
    operator fun invoke(): List<Dice> {
        val diceDrawables = listOf(
            R.drawable.dice_1,
            R.drawable.dice_2,
            R.drawable.dice_3,
            R.drawable.dice_4,
            R.drawable.dice_5,
            R.drawable.dice_6
        )

        return (0..5).map { index ->
            Dice(index, diceDrawables[Random.nextInt(until = 6)])
        }
    }
}

data class Dice(
    val value: Int,
    val image: Int
)