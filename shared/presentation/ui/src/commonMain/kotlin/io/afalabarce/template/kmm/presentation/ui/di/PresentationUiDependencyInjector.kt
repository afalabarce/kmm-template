package io.afalabarce.template.kmm.presentation.ui.di

import io.afalabarce.template.kmm.core.common.di.KoinModuleLoader
import io.afalabarce.template.kmm.core.di.CoreDiDependencyInjector
import org.koin.core.module.Module

object PresentationUiDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            CoreDiDependencyInjector.modules,
            listOf(

            )
        ).flatten()

}