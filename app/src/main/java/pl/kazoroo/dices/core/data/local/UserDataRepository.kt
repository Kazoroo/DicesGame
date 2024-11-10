package pl.kazoroo.dices.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class UserDataRepository(private val context: Context) {

    suspend fun saveNewValue(key: UserDataKey, value: String) {
        val dataStoreKey = stringPreferencesKey(key.name)

        context.dataStore.edit { userData ->
            userData[dataStoreKey] = value
        }
    }

    suspend fun readValue(key: UserDataKey): String? {
        val dataStoreKey = stringPreferencesKey(key.name)
        val preferences = context.dataStore.data.first()

        return preferences[dataStoreKey]
    }
}