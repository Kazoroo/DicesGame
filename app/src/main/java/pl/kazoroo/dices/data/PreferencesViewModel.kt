package pl.kazoroo.dices.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PreferencesViewModel(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {
    val preferencesState: StateFlow<DicePreferencesState> =
        userPreferencesRepository.layoutColor.map { layoutColor ->
            DicePreferencesState(layoutColor)
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DicePreferencesState()
        )

    fun saveLayoutColorRGB(layoutColorRed: String,
                           layoutColorGreen: String,
                           layoutColorBlue: String) {
        val layoutColors: Set<String> = setOf(layoutColorRed, layoutColorGreen, layoutColorBlue)

        viewModelScope.launch {
            userPreferencesRepository.saveColorPreference(layoutColors)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DiceApplication)
                PreferencesViewModel(application.userPreferencesRepository)
            }
        }
    }
}


data class DicePreferencesState(val layoutColor: Set<String> = setOf(
        "0.30588236", "0.16078432", "1.0"
))
