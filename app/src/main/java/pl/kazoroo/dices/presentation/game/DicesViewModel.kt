package pl.kazoroo.dices.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.kazoroo.dices.domain.model.DiceSetInfo
import pl.kazoroo.dices.domain.model.PointsState
import pl.kazoroo.dices.domain.usecase.CalculatePointsUseCase
import pl.kazoroo.dices.domain.usecase.CheckForSkuchaUseCase
import pl.kazoroo.dices.domain.usecase.DrawDiceUseCase
import pl.kazoroo.dices.presentation.navigation.Screen

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

    private val _userPointsState = MutableStateFlow(
        PointsState(
            selectedPoints = 0,
            roundPoints = 0,
            totalPoints = 0
        )
    )
    val userPointsState = _userPointsState.asStateFlow()

    private val _opponentPointsState = MutableStateFlow(
        PointsState(
            selectedPoints = 0,
            roundPoints = 0,
            totalPoints = 0
        )
    )
    val opponentPointsState = _opponentPointsState.asStateFlow()

    private val _skuchaState = MutableStateFlow(false)
    val skuchaState = _skuchaState.asStateFlow()

    private val _isOpponentTurn = MutableStateFlow(false)
    val isOpponentTurn = _isOpponentTurn.asStateFlow()

    private val _isGameEnd = MutableStateFlow(false)
    val isGameEnd = _isGameEnd.asStateFlow()

    fun toggleDiceSelection(index: Int) {
        _diceState.update { currentState ->
            val updatedDiceSelected = currentState.isDiceSelected.toMutableList().apply {
                this[index] = !this[index]
            }

            currentState.copy(isDiceSelected = updatedDiceSelected)
        }

        updateSelectedPointsState()
    }

    private fun updateSelectedPointsState() {
        val stateToUpdate = if (_isOpponentTurn.value) _opponentPointsState else _userPointsState

        stateToUpdate.update { currentState ->
            currentState.copy(
                selectedPoints = calculatePointsUseCase(
                    diceList = diceState.value.diceList,
                    isDiceSelected = diceState.value.isDiceSelected
                )
            )
        }
    }

    /**
     * Prepare the dice and points state for the next current player's throw.
     */
    fun prepareForNextThrow() {
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

        val stateToUpdate = if (_isOpponentTurn.value) _opponentPointsState else _userPointsState

        stateToUpdate.update { currentState ->
            currentState.copy(
                roundPoints = stateToUpdate.value.selectedPoints + stateToUpdate.value.roundPoints,
                selectedPoints = 0
            )
        }
    }

    fun checkForSkucha(navController: NavHostController) {
        val isSkucha = checkForSkuchaUseCase(
            diceState.value.diceList,
            diceState.value.isDiceVisible
        )

        if(startNewRoundIfAllDiceInvisible()) return

        if(isSkucha) {
            viewModelScope.launch {
                performSkuchaActions(navController)
            }
        }
    }

    private fun startNewRoundIfAllDiceInvisible(): Boolean {
        return if (diceState.value.isDiceVisible.all { !it }) {
            _diceState.update { currentState ->
                currentState.copy(
                    diceList = drawDiceUseCase(),
                    isDiceSelected = List(6) { false },
                    isDiceVisible = List(6) { true }
                )
            }
            true
        } else {
            false
        }
    }

    private suspend fun performSkuchaActions(navController: NavHostController) {
        val stateToUpdate = if (_isOpponentTurn.value) _opponentPointsState else _userPointsState

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

        stateToUpdate.update { currentState ->
            currentState.copy(
                roundPoints = 0
            )
        }

        if(_isOpponentTurn.value) {
            _isOpponentTurn.value = false
        } else {
            computerPlayerTurn(navController)
        }
    }

    fun passTheRound(navController: NavHostController) {
        viewModelScope.launch {
            val stateToUpdate =
                if (_isOpponentTurn.value) _opponentPointsState else _userPointsState

            stateToUpdate.update { currentState ->
                currentState.copy(
                    roundPoints = 0,
                    selectedPoints = 0,
                    totalPoints = stateToUpdate.value.selectedPoints + stateToUpdate.value.roundPoints + stateToUpdate.value.totalPoints
                )
            }

            if (stateToUpdate.value.totalPoints >= 4000) {
                performGameEndActions(navController)

                return@launch
            }

            _diceState.update { currentState ->
                currentState.copy(
                    diceList = drawDiceUseCase(),
                    isDiceSelected = List(6) { false },
                    isDiceVisible = List(6) { true }
                )
            }

            if (!isOpponentTurn.value) {
                computerPlayerTurn(navController)
            } else {
                _isOpponentTurn.value = false
            }
        }
    }

    private suspend fun performGameEndActions(navController: NavHostController) {
        _isGameEnd.value = true

        delay(3000L)

        _isGameEnd.value = false

        navController.navigate(Screen.MainScreen.withArgs()) {
            popUpTo(Screen.GameScreen.withArgs()) { inclusive = true }
        }

        delay(1000L)

        _diceState.update { currentState ->
            currentState.copy(
                diceList = drawDiceUseCase(),
                isDiceSelected = List(6) { false },
                isDiceVisible = List(6) { true }
            )
        }
        _opponentPointsState.update { currentState ->
            currentState.copy(
                selectedPoints = 0,
                roundPoints = 0,
                totalPoints = 0
            )
        }
        _userPointsState.update { currentState ->
            currentState.copy(
                selectedPoints = 0,
                roundPoints = 0,
                totalPoints = 0
            )
        }
        _isOpponentTurn.value = false
    }

    private fun computerPlayerTurn(navController: NavHostController) {
        _isOpponentTurn.value = true

        viewModelScope.launch(Dispatchers.Default) {
            while(diceState.value.isDiceVisible.count { it } > (2..4).random()) {
                val indexesOfDiceGivingPoints = searchForDiceIndexGivingPoints()

                delay((800L..1500L).random())

                if(indexesOfDiceGivingPoints.isEmpty()) {
                    performSkuchaActions(navController)

                    return@launch
                }

                for (i in indexesOfDiceGivingPoints.indices) {
                    toggleDiceSelection(indexesOfDiceGivingPoints[i])
                    delay((800L..1200L).random())
                }

                prepareForNextThrow()
            }

            withContext(Dispatchers.Main) {
                passTheRound(navController)
            }
        }
    }

    /**
     * @return List of dice indexes that gives points
     */
    private fun searchForDiceIndexGivingPoints(): List<Int> {
        val sequenceDice: List<Int> =
            diceState.value.diceList.filterIndexed { index, _ -> diceState.value.isDiceVisible[index] }
                .groupingBy { it.value }.eachCount().filter { it.value >= 3 }.keys.toList()

        val indexesOfDiceGivingPoints = diceState.value.diceList.mapIndexedNotNull { index, dice ->
            if ((dice.value == 1 || dice.value == 5 || sequenceDice.contains(dice.value)) && diceState.value.isDiceVisible[index]) index else null
        }.shuffled()

        return indexesOfDiceGivingPoints
    }
}