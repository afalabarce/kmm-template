package io.afalabarce.template.kmm.core.viewmodels

import androidx.lifecycle.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class KmmViewModel : ViewModel() {
    fun CoroutineScope.safeLaunch(
        onStart: () -> Unit,
        onFinish: () -> Unit,
        onError: (Throwable) -> Unit,
        onStartContext: CoroutineContext = Dispatchers.Main,
        onFinishContext: CoroutineContext = Dispatchers.Main,
        launchBody: suspend () -> Unit
    ): Job {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Napier.d(
                message = "KmmViewModel coroutine error",
                throwable = throwable
            )
            onError(throwable)
        }

        return this.launch(
            coroutineExceptionHandler
        ) {
            withContext(onStartContext) { onStart() }
            launchBody()
            withContext(onFinishContext) { onFinish() }
        }
    }
}