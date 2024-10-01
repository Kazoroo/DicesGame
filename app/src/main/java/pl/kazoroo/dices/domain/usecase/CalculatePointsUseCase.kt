package pl.kazoroo.dices.domain.usecase

import pl.kazoroo.dices.domain.model.Dice

class CalculatePointsUseCase {
    operator fun invoke(diceList: List<Dice>, isDiceSelected: List<Boolean>): Int {
        val diceValuesList: IntArray = diceList.mapIndexed { index, dice ->
            if (isDiceSelected[index]) {
                dice.value
            } else {
                0
            }

        }.toIntArray()

        return diceValuesList.toList().groupBy { it }.map { (value, occurrences) ->
            val count = occurrences.size
            when (value) {
                1 -> if (count < 3) 100 * count else 1000 * (count - 2)
                5 -> if (count < 3) 50 * count else 500 * (count - 2)
                else -> if (count >= 3) value * 100 * (count - 2) else 0
            }
        }.sum()
    }
}


