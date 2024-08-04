package io.afalabarce.template.kmm.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.afalabarce.template.kmm.core.common.platform.getPlatform
import kmmtemplate.shared.presentation.ui.generated.resources.Res
import kmmtemplate.shared.presentation.ui.generated.resources.app_name
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun App() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val platform = getPlatform()
                val appName = stringResource(Res.string.app_name)
                Text("$appName ${platform.platformData}")
            }
        }
    }
}