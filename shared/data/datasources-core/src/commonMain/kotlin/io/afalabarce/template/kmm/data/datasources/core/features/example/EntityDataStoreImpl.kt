package io.afalabarce.template.kmm.data.datasources.core.features.example

import io.afalabarce.template.kmm.data.datasources.features.example.EntityDataStore
import io.afalabarce.template.kmm.models.features.example.local.CacheExampleEntity
import kotlinx.coroutines.flow.Flow

class EntityDataStoreImpl(private val dao: ExampleDao): EntityDataStore {
    override fun getEntities(): Flow<List<CacheExampleEntity>> = dao.getAllEntities()
}