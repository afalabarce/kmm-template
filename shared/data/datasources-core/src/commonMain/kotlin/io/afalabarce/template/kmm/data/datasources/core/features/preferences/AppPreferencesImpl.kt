package io.afalabarce.template.kmm.data.datasources.core.features.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import io.afalabarce.template.kmm.data.datasources.features.preferences.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : AppPreferences {
    override fun getDeviceId(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[DEVICE_ID_KEY] ?: 0
    }

    override suspend fun setDeviceId(deviceId: Long) {
        dataStore.edit { preferences ->
            preferences[DEVICE_ID_KEY] = deviceId
        }
    }

    companion object {
        private val DEVICE_ID_KEY = longPreferencesKey("DeviceId")
    }
}