package io.afalabarce.template.kmm.data.datasources.core.db

import io.afalabarce.template.kmm.models.features.example.local.CacheExampleEntity


class Database(databaseFactory: DriverFactory) {
    private val database = KmmDatabase(databaseFactory.createDriver())
    private val dbQuery = database.kmmDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.deleteAll()
        }
    }

    internal fun deleteById(id: Long) {
        dbQuery.transaction {
            dbQuery.deleteById(id)
        }
    }

    internal fun getAllEntities(): List<CacheExampleEntity> {
        return dbQuery.selectAll(::exampleEntityMapper).executeAsList()
    }

    internal fun insertOrUpdateEntities(vararg entities: CacheExampleEntity) {
        dbQuery.transaction {
            entities.forEach { entity ->
                if (entity.id == 0L) {
                    dbQuery.insertItem(
                        entity.title,
                        entity.description
                    )
                } else {
                    dbQuery.updateItem(
                        entity.title,
                        entity.description,
                        entity.id
                    )
                }
            }
        }
    }

    private fun exampleEntityMapper(
        id: Long,
        title: String,
        description: String
    ): CacheExampleEntity = CacheExampleEntity(
        id = id,
        title = title,
        description = description
    )

    companion object {
        val databaseName = "KmmDatabase"
    }
}
