package io.afalabarce.template.kmm.domain.repository.features.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getDeviceId(): Flow<Long>
    suspend fun setDeviceId(deviceId: Long)
}