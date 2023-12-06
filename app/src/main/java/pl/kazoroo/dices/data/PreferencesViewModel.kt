package pl.kazoroo.dices.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import pl.kazoroo.dices.ui.theme.stringSetToColor

class PreferencesViewModel(private val userPreferencesRepository: UserPreferencesRepository) : ViewModel() {
    var showPopup by mutableStateOf(false)
        private set

    val preferencesState: StateFlow<DicePreferencesState> =
        userPreferencesRepository.layoutColor.map { layoutColor ->
            DicePreferencesState(layoutColor)
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DicePreferencesState()
        )


    private val fixedSizeList: FixedSizeList

    init {
        val initialItems = List(2) { stringSetToColor(preferencesState.value.layoutColor) }
        fixedSizeList = FixedSizeList(2, initialItems)
    }

    var lastColor by mutableStateOf(
        fixedSizeList.getItem()
    )
        private set

    fun saveLayoutColorRGB(layoutColorRed: String,
    layoutColorGreen: String,
    layoutColorBlue: String
    ) {
        println("fixedSizeList.getItem() in saveLayoutColorRGB - ${fixedSizeList.getItem()}")

        val setOfLayoutColors: Set<String> = setOf(layoutColorRed, layoutColorGreen, layoutColorBlue)
        val layoutColor = stringSetToColor(setOfLayoutColors)

        fixedSizeList.addItem(layoutColor)
        lastColor = fixedSizeList.getItem()

        viewModelScope.launch {
            userPreferencesRepository.saveColorPreference(setOfLayoutColors)
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
    "0.80588236", "0.16078432", "1.0"
))