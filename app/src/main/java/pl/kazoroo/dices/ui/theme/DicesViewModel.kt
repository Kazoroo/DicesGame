@file:Suppress("UnusedDataClassCopyResult")

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
        _uiState.value = DicesModel(dices = dices)
    }

    fun isSelectedBehavior(index: Int)
    {
        val list = _uiState.value.isSelected
        val updatedList = list.toMutableList()

        updatedList[index] = !list[index]
        updatedList.toList()

        dicesModel.isSelected = updatedList
        val isSelected = dicesModel.isSelected
        _uiState.value = DicesModel(isSelected = isSelected, dices = dicesModel.dices)
    }

    init {
        drawDice()
    }
}