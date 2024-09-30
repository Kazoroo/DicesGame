package pl.kazoroo.dices.domain.model

data class DiceSetInfo(
    val diceList: List<Dice>,
    val isDiceSelected: List<Boolean>,
    val isDiceVisible: List<Boolean>
)