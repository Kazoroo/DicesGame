package pl.kazoroo.dices.data

import pl.kazoroo.dices.R

data class DicesModel(var dices: List<Int> = listOf<Int>(
        R.drawable.dice_1,
        R.drawable.dice_1,
        R.drawable.dice_1,
        R.drawable.dice_1,
        R.drawable.dice_1,
        R.drawable.dice_1,
),
                      var isSelected: List<Boolean> = listOf(
                              false, false, false, false, false, false
                      ),
                      var points: Int = 0,
                      var shouldntDiceExist: List<Boolean> = listOf(
                              false, false, false, false, false, false
                      ),
                      var roundPoints: Int = 0,
                      var skucha: Boolean = false,
                      var sumOfPoints: Int = 0,
                      var showSkucha: Boolean = false)

var dicesModel = DicesModel()