package io.afalabarce.template.kmm.data.datasources.core.di

import io.afalabarce.template.kmm.core.common.di.KoinModuleLoader
import io.afalabarce.template.kmm.data.datasources.core.features.preferences.AppPreferencesImpl
import io.afalabarce.template.kmm.data.datasources.core.remote.ApiService
import io.afalabarce.template.kmm.data.datasources.features.preferences.AppPreferences
import de.jensklingenberg.ktorfit.Ktorfit
import io.afalabarce.template.kmm.data.datasources.core.db.AppDatabase
import io.afalabarce.template.kmm.data.datasources.core.db.AppDatabaseConstructor
import io.afalabarce.template.kmm.data.datasources.core.db.getExampleDao
import io.afalabarce.template.kmm.data.datasources.core.db.getRoomDatabase
import io.afalabarce.template.kmm.data.datasources.core.features.example.EntityDataStoreImpl
import io.afalabarce.template.kmm.data.datasources.core.features.example.ExampleDao
import io.afalabarce.template.kmm.data.datasources.core.remote.createApiService
import io.afalabarce.template.kmm.data.datasources.features.example.EntityDataStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun getPlatformInjects(): List<Module>

object DataSourceCoreDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = getPlatformInjects().union(
            listOf(
                module {
                    single<ApiService> {
                        Ktorfit
                            .Builder()
                            .baseUrl(ApiService.API_URL)
                            .build()
                            .createApiService()
                    }
                    singleOf(::getRoomDatabase)
                    singleOf(::getExampleDao)
                    singleOf(::AppPreferencesImpl) bind AppPreferences::class
                    factoryOf(::EntityDataStoreImpl) bind EntityDataStore::class
                    //single<AppPreferences> { AppPreferencesImpl(get()) }
                }
            )
        ).toList()
}
