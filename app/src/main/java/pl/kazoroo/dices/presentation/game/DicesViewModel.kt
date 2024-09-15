package pl.kazoroo.dices.presentation.game

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kazoroo.dices.domain.usecase.Dice
import pl.kazoroo.dices.domain.usecase.DrawDiceUseCase

data class DiceInfo(
    val diceList: List<Dice>,
    val isDiceSelected: List<Boolean>,
    val isDiceVisible: List<Boolean>
)

class DicesViewModel(
    drawDiceUseCase: DrawDiceUseCase = DrawDiceUseCase()
) : ViewModel() {

    private val _diceState = MutableStateFlow(
        DiceInfo(
            diceList = drawDiceUseCase(),
            isDiceSelected = List(6) { false },
            isDiceVisible = List(6) { true }
        )
    )
    val diceState = _diceState.asStateFlow()

    fun toggleDiceSelection(index: Int) {
        _diceState.update { currentState ->
            val updatedDiceSelected = currentState.isDiceSelected.toMutableList().apply {
                this[index] = !this[index]
            }

            currentState.copy(isDiceSelected = updatedDiceSelected)
        }
    }
}