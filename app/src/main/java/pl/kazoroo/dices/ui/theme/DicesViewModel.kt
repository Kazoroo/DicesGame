package pl.kazoroo.dices.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.kazoroo.dices.R
import kotlin.random.Random

class DicesViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(DicesModel())
    val uiState: StateFlow<DicesModel> = _uiState.asStateFlow()

    private var points: Int = 0
    private val dicesList = mutableListOf<String>()

    fun drawDice()
    {
        val listOfDices = mutableListOf<Int>()

        for(i in 0..5)
        {
            when(Random.nextInt(1,6))
            {
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

        points = 0

        val shouldntExist: MutableList<Boolean> = dicesModel.shouldntExist.toMutableList()

        for (i in 0..5)
        {
            if (dicesModel.isSelected[i] || dicesModel.shouldntExist[i])
            {
                shouldntExist[i] = true
            }
        }
        _uiState.value = DicesModel(dices = dices, shouldntExist = shouldntExist)
        dicesModel.shouldntExist = shouldntExist
    }

    fun isSelectedBehavior(index: Int)
    {
        val list = _uiState.value.isSelected
        val updatedList = list.toMutableList()

        updatedList[index] = !list[index]
        updatedList.toList()

        pointsCounter(dicesModel.dices[index], updatedList[index])

        dicesModel.isSelected = updatedList
        val isSelected = dicesModel.isSelected
        _uiState.value = DicesModel(isSelected = isSelected, dices = dicesModel.dices, points = dicesModel.points, shouldntExist = dicesModel.shouldntExist)
    }

    private fun pointsCounter(dice: Int, isSelected: Boolean) {

        var _points = points

        if(isSelected)
        {
            when(dice)
            {
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
        _uiState.value = DicesModel(isSelected = dicesModel.isSelected, dices = dicesModel.dices, points = points, shouldntExist = dicesModel.shouldntExist)
    }

    fun firstDrawDice()
    {
        val listOfDices = mutableListOf<Int>()

        for(i in 0..5)
        {
            when(Random.nextInt(1,6))
            {
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
        points = 0
        _uiState.value = DicesModel(dices = dices)
    }

    init {
        firstDrawDice()
    }
}