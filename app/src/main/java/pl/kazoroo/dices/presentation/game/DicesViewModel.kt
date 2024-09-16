package pl.kazoroo.dices.presentation.game

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kazoroo.dices.domain.model.DiceInfo
import pl.kazoroo.dices.domain.model.PointsState
import pl.kazoroo.dices.domain.usecase.CalculatePointsUseCase
import pl.kazoroo.dices.domain.usecase.DrawDiceUseCase


class DicesViewModel(
    drawDiceUseCase: DrawDiceUseCase = DrawDiceUseCase(),
    private val calculatePointsUseCase: CalculatePointsUseCase = CalculatePointsUseCase()
) : ViewModel() {
    private val _diceState = MutableStateFlow(
        DiceInfo(
            diceList = drawDiceUseCase(),
            isDiceSelected = List(6) { false },
            isDiceVisible = List(6) { true }
        )
    )
    val diceState = _diceState.asStateFlow()

    private val _pointsState = MutableStateFlow(
        PointsState(
            selectedPoints = 0,
            roundPoints = 0,
            totalPoints = 0
        )
    )
    val pointsState = _pointsState.asStateFlow()

    fun toggleDiceSelection(index: Int) {
        _diceState.update { currentState ->
            val updatedDiceSelected = currentState.isDiceSelected.toMutableList().apply {
                this[index] = !this[index]
            }

            currentState.copy(isDiceSelected = updatedDiceSelected)
        }
    }

    fun calculateScore() {
        val diceValuesList: IntArray = diceState.value.diceList.mapIndexed { index, dice ->
            if (diceState.value.isDiceSelected[index]) {
                dice.value
            } else {
                0
            }

        }.toIntArray()

        _pointsState.update { currentState ->
            currentState.copy(
                selectedPoints = calculatePointsUseCase(diceValuesList)
            )
        }
    }
}