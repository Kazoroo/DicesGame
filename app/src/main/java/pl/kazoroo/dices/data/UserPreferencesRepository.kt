package pl.kazoroo.dices.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val LAYOUT_COLOR = stringSetPreferencesKey("layout_color")
        const val TAG = "UserPreferencesRepo"
    }

    val layoutColor: Flow<Set<String>> = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        }
        else {
            throw it
        }
    }.map { preferences ->
        preferences[LAYOUT_COLOR] ?: setOf("0.3", "0.2", "1.0")
    }

    suspend fun saveColorPreference(layoutColors: Set<String>) {
        dataStore.edit { preferences ->
            preferences[LAYOUT_COLOR] = layoutColors
        }
    }
}