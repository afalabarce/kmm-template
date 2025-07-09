package io.afalabarce.template.kmm.data.repository.features.example

import io.afalabarce.template.kmm.data.datasources.features.example.EntityDataStore
import io.afalabarce.template.kmm.domain.models.ExampleEntity
import io.afalabarce.template.kmm.domain.repository.features.example.EntityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EntityRepositoryImpl (private val dataSource: EntityDataStore): EntityRepository {
    override fun getEntities(): Flow<List<ExampleEntity>> = dataSource.getEntities().map { data ->
        data.map { entity -> ExampleEntity(
            id = entity.id,
            title = entity.title,
            description = entity.description
        ) }
    }

}