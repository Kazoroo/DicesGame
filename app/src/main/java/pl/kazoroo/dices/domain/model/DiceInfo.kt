package pl.kazoroo.dices.domain.model

import pl.kazoroo.dices.domain.usecase.Dice

data class DiceInfo(
    val diceList: List<Dice>,
    val isDiceSelected: List<Boolean>,
    val isDiceVisible: List<Boolean>
)