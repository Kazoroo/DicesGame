package pl.kazoroo.dices.domain.usecase

class CalculatePointsUseCase {
    operator fun invoke(diceList: List<Dice>, isDiceSelected: List<Boolean>): Int {
        val diceValuesList: IntArray = diceList.mapIndexed { index, dice ->
            if (isDiceSelected[index]) {
                dice.value
            } else {
                0
            }

        }.toIntArray()

        return diceValuesList.groupBy { it }.map {
            val size = it.value.size

            when (it.key) {
                1 -> (size / 3) * 1000 + (size % 3) * 100
                5 -> (size / 3) * 500 + (size % 3) * 50
                else -> (size / 3) * 100 * it.key
            }
        }.sum()
    }
}


