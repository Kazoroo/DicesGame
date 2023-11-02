package pl.kazoroo.dices.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import pl.kazoroo.dices.R
import kotlin.random.Random

class DicesViewModel : ViewModel() {
    var dicesList by mutableStateOf(drawDice())
        private set
    var isDiceSelected by mutableStateOf(
            listOf(
                    false, false, false, false, false, false
            )
    )
        private set
    var shouldDiceExist by mutableStateOf(
            listOf(
                    true, true, true, true, true, true,
            )
    )
        private set
    var roundPoints by mutableIntStateOf(0)
        private set
    var points by mutableIntStateOf(0)
        private set
    var sumOfPoints by mutableIntStateOf(1950)
        private set
    var showSkucha by mutableStateOf(false)
        private set
    var gameEnd by mutableStateOf(false)
        private set

    private fun drawDice(): List<Int> {
        val listOfDices = mutableListOf<Int>()

        for (index in 0..5) {
            when (Random.nextInt(1, 7)) {
                1 -> listOfDices.add(index, R.drawable.dice_1)
                2 -> listOfDices.add(index, R.drawable.dice_2)
                3 -> listOfDices.add(index, R.drawable.dice_3)
                4 -> listOfDices.add(index, R.drawable.dice_4)
                5 -> listOfDices.add(index, R.drawable.dice_5)
                6 -> listOfDices.add(index, R.drawable.dice_6)
            }
        }
        return listOfDices
    }

    /**
     * Ends a queue. Check if there is skucha or win and sum a points. Reset all dices, roundPoints and points.
     * @param isSkucha app should show skucha
     * @return new uiState with reseted dices, points, roundpoints.
     */
    fun queueEndBehavior(isSkucha: Boolean = false) {
        if (!isSkucha) {
            sumOfPoints += points + roundPoints
        }

        if (sumOfPoints >= 2000) {
            gameEnd = true
        }

        shouldDiceExist = listOf(
                true, true, true, true, true, true,
        )
        roundPoints = 0
        points = 0
        showSkucha = true
        dicesList = drawDice()
        isDiceSelected = listOf(
                false, false, false, false, false, false
        )
        shouldDiceExist = listOf(
                true, true, true, true, true, true,
        )

        selectedDicesList.clear()
    }

    fun showSkuchaBehavior() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                showSkucha = false
                gameEnd = false
                sumOfPoints = 0
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                queueEndBehavior(true)
            }
        }
    }

    fun roundEndBehavior() {
        val dices = drawDice()
        roundPoints += points
        val _shouldDiceExist = shouldDiceExist.toMutableList()

        for (i in 0..5) {
            if (isDiceSelected[i] || !shouldDiceExist[i]) {
                _shouldDiceExist[i] = false
            }
        }
        shouldDiceExist = _shouldDiceExist

        fun <T> List<T>.findAllIndicesOf(value: T): List<Int> {
            return this.indices.filter { this[it] == value }
        }


        val indices = shouldDiceExist.findAllIndicesOf(true)

        val shouldBeSkucha = checkForSkucha(indices, dices)

        isDiceSelected = listOf(
                false, false, false, false, false, false
        )
        dicesList = dices
        roundPoints = roundPoints
        gameEnd = shouldBeSkucha.count { it } == shouldBeSkucha.size
        points = 0

        selectedDicesList.clear()
    }

    private fun checkForSkucha(indices: List<Int>, dices: List<Int>): List<Boolean> {
        val shouldBeSkucha = mutableListOf<Boolean>()

        for (i in indices) {
            if (dices[i] == R.drawable.dice_1 || dices[i] == R.drawable.dice_5 || dices.count { it == i } == 3) {
                shouldBeSkucha.add(false)
            }
            else {
                shouldBeSkucha.add(true)
            }
        }
        return shouldBeSkucha
    }

    fun isSelectedBehavior(index: Int) {
        val _isDiceSelected = isDiceSelected.toMutableList()

        _isDiceSelected[index] = !_isDiceSelected[index]
        _isDiceSelected.toList()

        runBlocking {
            launch {
                points = pointsCounter(dicesList[index], _isDiceSelected[index])
            }
        }

        isDiceSelected = _isDiceSelected
    }

    private val selectedDicesList: MutableList<String> = mutableListOf()
    private fun pointsCounter(dice: Int, isSelected: Boolean): Int {
        var _points = 0

        if (isSelected) {
            when (dice) {
                R.drawable.dice_1 -> selectedDicesList.add("1")
                R.drawable.dice_2 -> selectedDicesList.add("2")
                R.drawable.dice_3 -> selectedDicesList.add("3")
                R.drawable.dice_4 -> selectedDicesList.add("4")
                R.drawable.dice_5 -> selectedDicesList.add("5")
                R.drawable.dice_6 -> selectedDicesList.add("6")
            }
        } else {
            when (dice) {
                R.drawable.dice_1 -> selectedDicesList.remove("1")
                R.drawable.dice_2 -> selectedDicesList.remove("2")
                R.drawable.dice_3 -> selectedDicesList.remove("3")
                R.drawable.dice_4 -> selectedDicesList.remove("4")
                R.drawable.dice_5 -> selectedDicesList.remove("5")
                R.drawable.dice_6 -> selectedDicesList.remove("6")
            }
        }

        if (selectedDicesList.contains("1")) {
            _points += if (selectedDicesList.count { it == "1" } == 5) {
                3000
            }
            else if (selectedDicesList.count { it == "1" } == 4) {
                2000
            }
            else if (selectedDicesList.count { it == "1" } == 3) {
                1000
            }
            else {
                100
            }
        }

        if (selectedDicesList.contains("2")) {
            if (selectedDicesList.count { it == "2" } == 5) {
                _points += 800
            }
            else if (selectedDicesList.count { it == "2" } == 4) {
                _points += 400
            }
            else if (selectedDicesList.count { it == "2" } == 3) {
                _points += 200
            }
        }

        if (selectedDicesList.contains("3")) {
            if (selectedDicesList.count { it == "3" } == 5) {
                _points += 1200
            }
            else if (selectedDicesList.count { it == "3" } == 4) {
                _points += 600
            }
            else if (selectedDicesList.count { it == "3" } == 3) {
                _points += 300
            }
        }

        if (selectedDicesList.contains("4")) {
            if (selectedDicesList.count { it == "4" } == 5) {
                _points += 1600
            }
            else if (selectedDicesList.count { it == "4" } == 4) {
                _points += 800
            }
            else if (selectedDicesList.count { it == "4" } == 3) {
                _points += 400
            }
        }

        if (selectedDicesList.contains("5")) {
            _points += if (selectedDicesList.count { it == "5" } == 5) {
                2000
            }
            else if (selectedDicesList.count { it == "5" } == 4) {
                1000
            }
            else if (selectedDicesList.count { it == "5" } == 3) {
                500
            }
            else {
                50
            }
        }

        if (selectedDicesList.contains("6")) {
            if (selectedDicesList.count { it == "6" } == 5) {
                _points += 2400
            }
            else if (selectedDicesList.count { it == "6" } == 4) {
                _points += 1200
            }
            else if (selectedDicesList.count { it == "6" } == 3) {
                _points += 600
            }
        }
        return _points
    }
}