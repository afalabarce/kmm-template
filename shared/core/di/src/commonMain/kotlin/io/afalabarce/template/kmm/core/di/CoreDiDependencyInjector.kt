package io.afalabarce.template.kmm.core.di

import io.afalabarce.template.kmm.core.common.di.KoinModuleLoader
import io.afalabarce.template.kmm.data.datasources.core.di.DataSourceCoreDependencyInjector
import io.afalabarce.template.kmm.data.repository.di.DataRepositoryDependencyInjector
import io.afalabarce.template.kmm.domain.usecases.di.DomainUseCasesDependencyInjector
import io.afalabarce.template.kmm.presentation.viewmodels.di.PresentationViewModelsDependencyInjector
import org.koin.core.module.Module

object CoreDiDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            DataSourceCoreDependencyInjector.modules,
            DataRepositoryDependencyInjector.modules,
            DomainUseCasesDependencyInjector.modules,
            PresentationViewModelsDependencyInjector.modules,
        ).flatten()
}
