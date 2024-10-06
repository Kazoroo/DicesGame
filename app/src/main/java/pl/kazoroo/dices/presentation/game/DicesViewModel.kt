package pl.kazoroo.dices.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kazoroo.dices.domain.model.DiceSetInfo
import pl.kazoroo.dices.domain.model.PointsState
import pl.kazoroo.dices.domain.usecase.CalculatePointsUseCase
import pl.kazoroo.dices.domain.usecase.CheckForSkuchaUseCase
import pl.kazoroo.dices.domain.usecase.DrawDiceUseCase

class DicesViewModel(
    private val drawDiceUseCase: DrawDiceUseCase = DrawDiceUseCase(),
    private val calculatePointsUseCase: CalculatePointsUseCase = CalculatePointsUseCase(),
    private val checkForSkuchaUseCase: CheckForSkuchaUseCase = CheckForSkuchaUseCase()
) : ViewModel() {
    private val _diceState = MutableStateFlow(
        DiceSetInfo(
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

    private val _skuchaState = MutableStateFlow(false)
    val skuchaState = _skuchaState.asStateFlow()

    fun toggleDiceSelection(index: Int) {
        _diceState.update { currentState ->
            val updatedDiceSelected = currentState.isDiceSelected.toMutableList().apply {
                this[index] = !this[index]
            }

            currentState.copy(isDiceSelected = updatedDiceSelected)
        }
    }

    fun calculateScore() {
        _pointsState.update { currentState ->
            currentState.copy(
                selectedPoints = calculatePointsUseCase(
                    diceList = diceState.value.diceList,
                    isDiceSelected = diceState.value.isDiceSelected
                )
            )
        }
    }

    fun countPoints() {
        val newIsDiceVisible = diceState.value.isDiceVisible.toMutableList()

        for (i in diceState.value.isDiceVisible.indices) {
            if (diceState.value.isDiceVisible[i] && diceState.value.isDiceSelected[i]) {
                newIsDiceVisible[i] = false
            }
        }

        _diceState.update { currentState ->
            currentState.copy(
                diceList = drawDiceUseCase(),
                isDiceSelected = List(6) { false },
                isDiceVisible = newIsDiceVisible
            )
        }

        _pointsState.update { currentState ->
            currentState.copy(
                roundPoints = pointsState.value.selectedPoints + pointsState.value.roundPoints,
                selectedPoints = 0
            )
        }
    }

    fun checkForSkucha() {
        val isSkucha = checkForSkuchaUseCase(
            diceState.value.diceList,
            diceState.value.isDiceVisible
        )

        if(diceState.value.isDiceVisible.all { !it }) {
            _diceState.update { currentState ->
                currentState.copy(
                    diceList = drawDiceUseCase(),
                    isDiceSelected = List(6) { false },
                    isDiceVisible = List(6) { true }
                )
            }

            return
        }

        if(isSkucha) {
            viewModelScope.launch {
                delay(1000L)
                _skuchaState.value = true
                delay(2000L)

                _skuchaState.value = false

                _diceState.update { currentState ->
                    currentState.copy(
                        diceList = drawDiceUseCase(),
                        isDiceSelected = List(6) { false },
                        isDiceVisible = List(6) { true }
                    )
                }

                _pointsState.update { currentState ->
                    currentState.copy(
                        roundPoints = 0
                    )
                }
            }
        }
    }

    fun passTheRound() {
        _pointsState.update { currentState ->
            currentState.copy(
                roundPoints = 0,
                selectedPoints = 0,
                totalPoints = pointsState.value.selectedPoints + pointsState.value.roundPoints + pointsState.value.totalPoints
            )
        }

        _diceState.update { currentState ->
            currentState.copy(
                diceList = drawDiceUseCase(),
                isDiceSelected = List(6) { false },
                isDiceVisible = List(6) { true }
            )
        }
    }
}