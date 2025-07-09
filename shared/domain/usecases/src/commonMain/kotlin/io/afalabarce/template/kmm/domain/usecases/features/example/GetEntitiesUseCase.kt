package io.afalabarce.template.kmm.domain.usecases.features.example

import io.afalabarce.template.kmm.domain.models.ExampleEntity
import io.afalabarce.template.kmm.domain.repository.features.example.EntityRepository
import kotlinx.coroutines.flow.Flow

class GetEntitiesUseCase(private val repository: EntityRepository) {
    operator fun invoke(): Flow<List<ExampleEntity>> = repository.getEntities()
}