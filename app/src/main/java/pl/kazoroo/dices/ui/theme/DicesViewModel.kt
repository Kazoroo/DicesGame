package pl.kazoroo.dices.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.kazoroo.dices.R
import kotlin.random.Random

class DicesViewModel: ViewModel() {
    var UiState by mutableStateOf(mutableListOf<Int>(R.drawable.dice_4, R.drawable.dice_1, R.drawable.dice_5, R.drawable.dice_2, R.drawable.dice_1, R.drawable.dice_6))
        private set

    fun drawDice()
    {
        val listOfDices = mutableListOf<Int>()

        for(i in 0..5)
        {
            var number = Random.nextInt(1,6)
            when(number)
            {
                1 -> listOfDices.add(i, R.drawable.dice_1)
                2 -> listOfDices.add(i, R.drawable.dice_2)
                3 -> listOfDices.add(i, R.drawable.dice_3)
                4 -> listOfDices.add(i, R.drawable.dice_4)
                5 -> listOfDices.add(i, R.drawable.dice_5)
                6 -> listOfDices.add(i, R.drawable.dice_6)
            }
        }

        UiState = listOfDices
    }

}
