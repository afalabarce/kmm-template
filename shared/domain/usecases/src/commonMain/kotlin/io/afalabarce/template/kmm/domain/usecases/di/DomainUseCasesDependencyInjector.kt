package io.afalabarce.template.kmm.domain.usecases.di

import io.afalabarce.template.kmm.core.common.di.KoinModuleLoader
import io.afalabarce.template.kmm.domain.usecases.features.preferences.GetDeviceIdUseCase
import io.afalabarce.template.kmm.domain.usecases.features.preferences.SetDeviceIdUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object DomainUseCasesDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            module {
                factoryOf(::GetDeviceIdUseCase)
                factoryOf(::SetDeviceIdUseCase)
            }
        )
}
