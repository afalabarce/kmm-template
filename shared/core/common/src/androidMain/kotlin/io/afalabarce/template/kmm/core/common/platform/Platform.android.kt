package io.afalabarce.template.kmm.core.common.platform

import android.os.Build
import android.annotation.SuppressLint
import android.content.Context
import io.afalabarce.template.kmm.core.common.platform.entities.PlatformData
import io.afalabarce.template.kmm.core.common.platform.enums.PlatformType

class AndroidPlatform : Platform {
    override val platformData: PlatformData
        get() = PlatformData(
            platformType = PlatformType.ANDROID,
            osVersion = Build.VERSION.SDK_INT.toString()
        )

    companion object {
        @SuppressLint("StaticFieldLeak")
        var androidContext: Context? = null
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()