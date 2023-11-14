package pl.kazoroo.dices.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class UserPreferencesRepositoryTest {
    @get:Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())

    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private lateinit var testDataStoreFile: File

    @Before
    fun setUp() {
        testDataStoreFile = temporaryFolder.newFile("user.preferences_pb")

        val testDataStore: DataStore<Preferences> =
            PreferenceDataStoreFactory.create(scope = testScope,
                    produceFile = { testDataStoreFile })
        userPreferencesRepository = UserPreferencesRepository(testDataStore)
    }

    @Test
    fun returnDefaultValueForTheFirstTime() = testScope.runTest {
        val actual = userPreferencesRepository.layoutColor.first()

        assertEquals(setOf("0.3", "0.2", "1.0"), actual)
    }

    @Test
    fun layoutColorChangesValueWhenChanges() = testScope.runTest {
        val color = setOf("0.1", "0.1", "1.3")

        userPreferencesRepository.saveColorPreference(color)
        assertEquals(color, userPreferencesRepository.layoutColor.first())
    }
}