package pl.kazoroo.dices.domain.usecase

import pl.kazoroo.dices.domain.model.Dice

class CalculatePointsUseCase {
    operator fun invoke(
        diceList: List<Dice>,
        isDiceSelected: List<Boolean>,
        includeNonScoringDice: Boolean = true
    ): Int {
        val valuesOfSelectedDicesList: List<Int> = diceList.mapIndexed { index, dice ->
            if (isDiceSelected[index]) dice.value else 0
        }.filter { it > 0 }

        val occurrencesMap: Map<Int, Int> = valuesOfSelectedDicesList.groupingBy { it }.eachCount()
        var points = 0
        var nonScoringDice: List<Int> = listOf()

        when (valuesOfSelectedDicesList.sorted()) {
            listOf(1, 2, 3, 4, 5, 6) -> points += 1500
            listOf(2, 3, 4, 5, 6) -> points += 750
            listOf(1, 2, 3, 4, 5) -> points += 500
        }

        if(points == 0) {
            points = calculatePointsForSingleDiceValues(occurrencesMap, points)

            nonScoringDice = occurrencesMap.keys.filter { value ->
                (value != 1 && value != 5 && (occurrencesMap[value] ?: 0) < 3)
            }
        }


        return if(nonScoringDice.isEmpty() || !includeNonScoringDice) points else 0
    }

    private fun calculatePointsForSingleDiceValues(
        occurrencesMap: Map<Int, Int>,
        points: Int
    ): Int {
        var singleDiceValuePoints = points

        occurrencesMap.forEach { (value, count) ->
            when (value) {
                1 -> {
                    singleDiceValuePoints += if (count < 3) {
                        100 * count
                    } else {
                        1000 * (count - 2)
                    }
                }

                5 -> {
                    singleDiceValuePoints += if (count < 3) {
                        50 * count
                    } else {
                        500 * (count - 2)
                    }
                }

                else -> {
                    singleDiceValuePoints += if (count >= 3) {
                        value * 100 * (count - 2)
                    } else {
                        0
                    }
                }
            }
        }

        return singleDiceValuePoints
    }
}
