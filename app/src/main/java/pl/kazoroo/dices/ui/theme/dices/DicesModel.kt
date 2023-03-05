package pl.kazoroo.dices.ui.theme.dices

import pl.kazoroo.dices.R
import kotlin.random.Random

data class DicesModel(
    var dices: List<Int> = listOf<Int>(
            Random.nextInt(R.drawable.dice_1, R.drawable.dice_6),
            Random.nextInt(R.drawable.dice_1, R.drawable.dice_6),
            Random.nextInt(R.drawable.dice_1, R.drawable.dice_6),
            Random.nextInt(R.drawable.dice_1, R.drawable.dice_6),
            Random.nextInt(R.drawable.dice_1, R.drawable.dice_6),
            Random.nextInt(R.drawable.dice_1, R.drawable.dice_6)),
    var isSelected : List<Boolean> = listOf(false, false, false, false, false, false),
    var points: Int = 0,
    var shouldntExist: List<Boolean> = listOf(false, false, false, false, false, false)
)

var dicesModel = DicesModel()

//R.drawable.dice_1, R.drawable.dice_1, R.drawable.dice_1, R.drawable.dice_1, R.drawable.dice_1, R.drawable.dice_1