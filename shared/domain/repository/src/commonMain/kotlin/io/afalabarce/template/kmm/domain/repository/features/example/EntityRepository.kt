package io.afalabarce.template.kmm.domain.repository.features.example

import io.afalabarce.template.kmm.domain.models.ExampleEntity
import kotlinx.coroutines.flow.Flow

interface EntityRepository {
    fun getEntities(): Flow<List<ExampleEntity>>
}