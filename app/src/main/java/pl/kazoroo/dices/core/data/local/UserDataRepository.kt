package pl.kazoroo.dices.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

//TODO: private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class UserDataRepository(private val dataStore: DataStore<Preferences>) {

    suspend fun saveNewValue(key: UserDataKey, value: String) {
        val dataStoreKey = stringPreferencesKey(key.name)

        dataStore.edit { userData ->
            userData[dataStoreKey] = value
        }
    }

    suspend fun readValue(key: UserDataKey): String? {
        val dataStoreKey = stringPreferencesKey(key.name)
        val preferences = dataStore.data.first()

        return preferences[dataStoreKey]
    }
}