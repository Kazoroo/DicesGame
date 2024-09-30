package pl.kazoroo.dices.domain.usecase

import pl.kazoroo.dices.domain.model.Dice

class CheckForSkuchaUseCase {
    operator fun invoke(diceList: List<Dice>, isDiceVisible: List<Boolean>): Boolean {
        val points = CalculatePointsUseCase().invoke(
            diceList,
            isDiceVisible
        )

        return points == 0
    }
}