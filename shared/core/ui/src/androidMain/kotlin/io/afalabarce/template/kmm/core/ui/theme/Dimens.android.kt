package io.afalabarce.template.kmm.core.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.afalabarce.template.kmm.core.common.platform.AndroidPlatform

@SuppressLint("InternalInsetResource", "DiscouragedApi")
actual fun statusBarSize(): Dp {
    var statusBarHeight: Dp = 50.dp

    if (AndroidPlatform.androidContext != null) {
        val context: Context = AndroidPlatform.androidContext!!
        val resourceId = context.resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )

        if (resourceId != 0) {
            val pixelHeight =
                context.resources.getDimensionPixelSize(resourceId) / context.resources.displayMetrics.density

            statusBarHeight = pixelHeight.dp //Convertir a dp los pixels
        }
    }

    return statusBarHeight
}
