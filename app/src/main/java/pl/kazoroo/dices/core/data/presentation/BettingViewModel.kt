package pl.kazoroo.dices.core.data.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.kazoroo.dices.core.domain.SaveUserDataUseCase

class BettingViewModel(
    private val saveUserDataUseCase: SaveUserDataUseCase
) : ViewModel() {
    private val _betValue = MutableStateFlow("0")
    val betValue = _betValue.asStateFlow()

    private val _coinsAmount = MutableStateFlow(readCoinsAmount())
    val coinsAmount = _coinsAmount.asStateFlow()

    fun setBetValue(value: String) {
        _betValue.value = value

        viewModelScope.launch {
            saveUserDataUseCase.invoke(value)
        }
    }

    private fun readCoinsAmount(): String {
        return ""
    }
}