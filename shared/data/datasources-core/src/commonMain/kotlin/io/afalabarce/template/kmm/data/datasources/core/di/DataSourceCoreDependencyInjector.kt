package io.afalabarce.template.kmm.data.datasources.core.di

import io.afalabarce.template.kmm.core.common.di.KoinModuleLoader
import io.afalabarce.template.kmm.data.datasources.core.db.Database
import io.afalabarce.template.kmm.data.datasources.core.features.preferences.AppPreferencesImpl
import io.afalabarce.template.kmm.data.datasources.core.remote.ApiService
import io.afalabarce.template.kmm.data.datasources.features.preferences.AppPreferences
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
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
                            .create()
                    }
                    singleOf(::Database)
                    single<AppPreferences> { AppPreferencesImpl(get()) }
                }
            )
        ).toList()
}
