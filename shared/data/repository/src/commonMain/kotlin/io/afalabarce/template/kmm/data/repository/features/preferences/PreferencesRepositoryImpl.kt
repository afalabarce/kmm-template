package io.afalabarce.template.kmm.data.repository.features.preferences

import io.afalabarce.template.kmm.data.datasources.features.preferences.AppPreferences
import io.afalabarce.template.kmm.domain.repository.features.preferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(private val preferences: AppPreferences) : PreferencesRepository {
    override fun getDeviceId(): Flow<Long> = this.preferences.getDeviceId()

    override suspend fun setDeviceId(deviceId: Long) = this.preferences.setDeviceId(deviceId)

}