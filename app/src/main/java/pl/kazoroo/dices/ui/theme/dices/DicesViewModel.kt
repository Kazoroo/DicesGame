package pl.kazoroo.dices.ui.theme.dices

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pl.kazoroo.dices.R
import kotlin.random.Random

class DicesViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(DicesModel())
    val uiState: StateFlow<DicesModel> = _uiState.asStateFlow()
    private val dicesList = mutableListOf<String>()

    fun turnEndBehavior() {
        var sumOfPoints = dicesModel.sumOfPoints

        sumOfPoints += dicesModel.points + dicesModel.roundPoints

        dicesModel.shouldntExist = DicesModel().shouldntExist
        dicesModel.roundPoints = DicesModel().roundPoints
        dicesModel.points = DicesModel().points
        dicesModel.sumOfPoints = sumOfPoints

        _uiState.value = DicesModel(
                dices = drawDice(),
                sumOfPoints = sumOfPoints,
                shouldntExist = DicesModel().shouldntExist,
                points = DicesModel().points,
                roundPoints = DicesModel().roundPoints
        )
    }

    private fun drawDice(): List<Int> {
        val listOfDices = mutableListOf<Int>()

        for (i in 0..5) {
            when (Random.nextInt(1, 7)) {
                1 -> listOfDices.add(i, R.drawable.dice_1)
                2 -> listOfDices.add(i, R.drawable.dice_2)
                3 -> listOfDices.add(i, R.drawable.dice_3)
                4 -> listOfDices.add(i, R.drawable.dice_4)
                5 -> listOfDices.add(i, R.drawable.dice_5)
                6 -> listOfDices.add(i, R.drawable.dice_6)
            }
        }

        dicesModel.dices = listOfDices
        val dices = dicesModel.dices
        dicesList.clear()
        _uiState.value = DicesModel(dices = dices)

        return dices
    }

    fun queueEndBehavior() {
        val dices = drawDice() //draw new dices

        var roundPoints = dicesModel.roundPoints
        roundPoints += dicesModel.points
        dicesModel.roundPoints = roundPoints

        val shouldntExist: MutableList<Boolean> = dicesModel.shouldntExist.toMutableList()

        for (i in 0..5) {
            if (dicesModel.isSelected[i] || dicesModel.shouldntExist[i]) {
                shouldntExist[i] = true
            }
        }
        dicesModel.shouldntExist = shouldntExist

        fun <T> List<T>.findAllIndicesOf(value: T): List<Int> {
            return this.indices.filter { this[it] == value }
        }

        val shouldBeSkucha = mutableListOf<Boolean>()
        val indices =
            shouldntExist.findAllIndicesOf(false) // return list of indexes where occurs 'false'

        for (i in indices) {
            if (dices[i] == R.drawable.dice_1 || dices[i] == R.drawable.dice_5 || dices.count { it == i } == 3) {
                shouldBeSkucha.add(false)
            }
            else {
                shouldBeSkucha.add(true)
            }
        }

        //if all indexes in shouldBeSkucha are true, game end
        if (shouldBeSkucha.count { it } == shouldBeSkucha.size) {
            dicesModel.points = 0
            roundPoints = 0
            _uiState.value = DicesModel(
                    dices = dices,
                    shouldntExist = shouldntExist,
                    roundPoints = roundPoints,
                    skucha = true,
                    sumOfPoints = dicesModel.sumOfPoints
            )
        }
        else {
            _uiState.value = DicesModel(
                    dices = dices,
                    shouldntExist = shouldntExist,
                    roundPoints = roundPoints,
                    skucha = false
            )
        }
    }

    fun isSelectedBehavior(index: Int) {
        val updatedList = _uiState.value.isSelected.toMutableList()

        updatedList[index] = !updatedList[index]
        updatedList.toList()

        runBlocking { launch { pointsCounter(dicesModel.dices[index], updatedList[index]) } }

        dicesModel.isSelected = updatedList
        val isSelected = dicesModel.isSelected
        _uiState.value = DicesModel(
                isSelected = isSelected,
                dices = dicesModel.dices,
                points = dicesModel.points,
                shouldntExist = dicesModel.shouldntExist,
                roundPoints = dicesModel.roundPoints,
                sumOfPoints = dicesModel.sumOfPoints
        )
    }

    private fun pointsCounter(dice: Int, isSelected: Boolean): Int {
        var _points = 0

        if (isSelected) {
            when (dice) {
                R.drawable.dice_1 -> dicesList.add("1")
                R.drawable.dice_2 -> dicesList.add("2")
                R.drawable.dice_3 -> dicesList.add("3")
                R.drawable.dice_4 -> dicesList.add("4")
                R.drawable.dice_5 -> dicesList.add("5")
                R.drawable.dice_6 -> dicesList.add("6")
            }
        } else {
            when(dice)
            {
                R.drawable.dice_1 -> dicesList.remove("1")
                R.drawable.dice_2 -> dicesList.remove("2")
                R.drawable.dice_3 -> dicesList.remove("3")
                R.drawable.dice_4 -> dicesList.remove("4")
                R.drawable.dice_5 -> dicesList.remove("5")
                R.drawable.dice_6 -> dicesList.remove("6")
            }
        }

        if (dicesList.contains("1")) {
            _points += if (dicesList.count { it == "1" } == 5) {
                3000
            }
            else if(dicesList.count { it == "1"} == 4) {
                2000
            }
            else if (dicesList.count { it == "1" } == 3) {
                1000
            }
            else {
                100
            }
        }

        if (dicesList.contains("2")) {
            if (dicesList.count { it == "2" } == 5) {
                _points += 800
            }
            else if(dicesList.count { it == "2"} == 4) {
                _points += 400
            }
            else if (dicesList.count { it == "2" } == 3) {
                _points += 200
            }
        }

        if (dicesList.contains("3")) {
            if (dicesList.count { it == "3" } == 5) {
                _points += 1200
            }
            else if(dicesList.count { it == "3"} == 4) {
                _points += 600
            }
            else if (dicesList.count { it == "3" } == 3) {
                _points += 300
            }
        }

        if (dicesList.contains("4")) {
            if (dicesList.count { it == "4" } == 5) {
                _points += 1600
            }
            else if(dicesList.count { it == "4"} == 4) {
                _points += 800
            }
            else if (dicesList.count { it == "4" } == 3) {
                _points += 400
            }
        }

        if (dicesList.contains("5")) {
            _points += if (dicesList.count { it == "5" } == 5) {
                2000
            }
            else if(dicesList.count { it == "5"} == 4) {
                1000
            }
            else if (dicesList.count { it == "5" } == 3) {
                500
            }
            else {
                50
            }
        }

        if (dicesList.contains("6")) {
            if (dicesList.count { it == "6" } == 5) {
                _points += 2400
            }
            else if(dicesList.count { it == "6"} == 4) {
                _points += 1200
            }
            else if (dicesList.count { it == "6" } == 3) {
                _points += 600
            }
        }

        dicesModel.points = _points
        val points = dicesModel.points
        _uiState.value = DicesModel(
                isSelected = dicesModel.isSelected,
                dices = dicesModel.dices,
                points = points,
                shouldntExist = dicesModel.shouldntExist,
                roundPoints = dicesModel.roundPoints
        )

        return points
    }
}