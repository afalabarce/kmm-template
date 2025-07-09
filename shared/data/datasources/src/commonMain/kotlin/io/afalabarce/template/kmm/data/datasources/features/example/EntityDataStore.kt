package io.afalabarce.template.kmm.data.datasources.features.example

import io.afalabarce.template.kmm.models.features.example.local.CacheExampleEntity
import kotlinx.coroutines.flow.Flow

interface EntityDataStore {
    fun getEntities(): Flow<List<CacheExampleEntity>>
}