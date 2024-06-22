package io.afalabarce.template.kmm

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import io.afalabarce.template.kmm.presentation.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val localView = LocalView.current

            val isDarkMode = isSystemInDarkTheme()

            if (!localView.isInEditMode) {
                val window = (localView.context as Activity).window

                window.statusBarColor = Color.Transparent.toArgb()
                WindowCompat.getInsetsController(window, localView).isAppearanceLightStatusBars = !isDarkMode
            }
            App()
        }
    }
}
