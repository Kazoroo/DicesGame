package pl.kazoroo.dices.data

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class DicesViewModelTest {
    private lateinit var viewModel: DicesViewModel

    @Before
    fun setUp() {
        viewModel = DicesViewModel()
    }

    @Test
    fun `queueEndBehavior(false) correctly calculates sumOfPoints`() {
        viewModel.isSelectedBehavior(2)
        viewModel.isSelectedBehavior(3)
        viewModel.isSelectedBehavior(4)

        val points = viewModel.points
        val roundPoints = viewModel.roundPoints
        val sumOfPoints = points + roundPoints

        viewModel.queueEndBehavior(false)
        assertThat(viewModel.sumOfPoints).isEqualTo(sumOfPoints)
    }

    @Test
    fun `showSkuchaTextBehavior sets correctly value of variables`() {
        viewModel.showSkuchaBehavior()
        assertThat(viewModel.showSkucha && viewModel.gameEnd).isFalse()
    }

    @Test
    fun roundEndBehavior() {
        viewModel.roundEndBehavior()

        assertThat(viewModel.roundPoints).isEqualTo(viewModel.points + viewModel.roundPoints)
    }

    @Test
    fun `checkIfDiceShouldExist() works correctly`() {
        viewModel.isSelectedBehavior(1)
        viewModel.isSelectedBehavior(3)
        viewModel.isSelectedBehavior(4)

        viewModel.roundEndBehavior()

        viewModel.isSelectedBehavior(1)
        viewModel.isSelectedBehavior(5)

        assertThat(viewModel.shouldDiceExist).isEqualTo(
                listOf(
                        true, false, true, false, false, true
                )
        )
    }

    @Test
    fun `check if isSelectedBehavior() changes isDiceSelected correctly`() {
        val selectedDice = viewModel.isDiceSelected[3]
        viewModel.isSelectedBehavior(3)

        assertThat(selectedDice).isNotEqualTo(viewModel.isDiceSelected[3])
    }
}