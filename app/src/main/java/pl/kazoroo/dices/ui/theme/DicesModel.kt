package pl.kazoroo.dices.ui.theme

import pl.kazoroo.dices.R

data class DicesModel(
    var dices: List<Int> = listOf<Int>(R.drawable.dice_1, R.drawable.dice_1, R.drawable.dice_1, R.drawable.dice_1, R.drawable.dice_1, R.drawable.dice_1),
    var isSelected : List<Boolean> = listOf(false, false, false, false, false, false)
)

var dicesModel = DicesModel()