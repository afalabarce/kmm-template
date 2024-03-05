package io.afalabarce.template.kmm.core.common.di

import org.koin.core.module.Module

interface KoinModuleLoader {
    val modules: List<Module>
}