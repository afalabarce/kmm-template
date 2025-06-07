package io.afalabarce.template.kmm.presentation.viewmodels.di

import io.afalabarce.template.kmm.presentation.viewmodels.features.example.EntityExampleViewModel
import io.afalabarce.template.kmm.core.common.di.KoinModuleLoader
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object PresentationViewModelsDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            module {
                viewModelOf(::EntityExampleViewModel)
            }
        )
}
