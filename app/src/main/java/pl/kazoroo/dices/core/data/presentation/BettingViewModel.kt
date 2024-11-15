package pl.kazoroo.dices.core.data.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.kazoroo.dices.core.domain.ReadUserDataUseCase
import pl.kazoroo.dices.core.domain.SaveUserDataUseCase

class BettingViewModel(
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val readUserDataUseCase: ReadUserDataUseCase
) : ViewModel() {
    private val _betValue = MutableStateFlow("0")
    val betValue = _betValue.asStateFlow()

    private val _coinsAmount = MutableStateFlow("")
    val coinsAmount = _coinsAmount.asStateFlow()

    init {
        viewModelScope.launch {
            _coinsAmount.value = readCoinsAmount()
        }
    }

    fun setBetValue(value: String) {
        _betValue.value = value

        viewModelScope.launch {
            val coins = readCoinsAmount()
            val newCoinBalance = (coins.toInt() - value.toInt()).toString()
            saveUserDataUseCase.invoke(newCoinBalance)
            readCoinsAmount()
        }
    }

    private suspend fun readCoinsAmount(): String {
        val coins = readUserDataUseCase.invoke()

        _coinsAmount.value = coins

        return coins
    }
}