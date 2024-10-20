package pl.kazoroo.dices.presentation.game

import org.junit.Before
import org.junit.Test

class DicesViewModelTest {

    private lateinit var viewModel: DicesViewModel

    @Before
    fun initiate() {
        viewModel = DicesViewModel()
    }

    @Test
    fun toggleDiceSelection() {
        viewModel.toggleDiceSelection(1)
        viewModel.toggleDiceSelection(5)

        assert(viewModel.diceState.value.isDiceSelected == listOf(false, true, false, false, false, true))
    }

    @Test
    fun prepareForNextThrow() {
        viewModel.toggleDiceSelection(1)
        viewModel.toggleDiceSelection(5)

        viewModel.prepareForNextThrow()

        assert(viewModel.diceState.value.isDiceVisible == listOf(true, false, true, true, true, false))

        viewModel.toggleDiceSelection(3)

        viewModel.prepareForNextThrow()

        assert(viewModel.diceState.value.isDiceVisible == listOf(true, false, true, false, true, false))
    }
}