package io.afalabarce.template.kmm.presentation.viewmodels.features.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.afalabarce.template.kmm.domain.usecases.features.example.GetEntitiesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class EntityExampleViewModel(private val getEntitiesUseCase: GetEntitiesUseCase): ViewModel() {

    val entities by lazy { getEntitiesUseCase().shareIn(viewModelScope, SharingStarted.Lazily) }
}