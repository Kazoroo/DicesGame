package pl.kazoroo.dices.domain.usecase

class CalculatePointsUseCase {
    operator fun invoke(diceValuesList: IntArray): Int {
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


