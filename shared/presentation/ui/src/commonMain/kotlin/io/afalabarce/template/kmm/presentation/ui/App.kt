package io.afalabarce.template.kmm.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.afalabarce.template.kmm.core.common.platform.getPlatform
import io.afalabarce.template.kmm.core.ui.theme.AppMaterialTheme
import io.afalabarce.template.kmm.domain.models.ExampleEntity
import io.afalabarce.template.kmm.presentation.viewmodels.features.example.EntityExampleViewModel
import kmmtemplate.shared.presentation.ui.generated.resources.Res
import kmmtemplate.shared.presentation.ui.generated.resources.app_name
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun App() {
    val viewModel: EntityExampleViewModel = koinViewModel()
    AppMaterialTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val data: List<ExampleEntity> by viewModel.entities.collectAsStateWithLifecycle(emptyList())

                val platform = getPlatform()
                val appName = stringResource(Res.string.app_name)
                Text("$appName ${platform.platformData}")
            }
        }
    }
}