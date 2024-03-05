package io.afalabarce.template.kmm.core.common.platform

import io.afalabarce.template.kmm.core.common.platform.entities.PlatformData
import io.afalabarce.template.kmm.core.common.platform.enums.PlatformType
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val platformData: PlatformData
        get() = PlatformData(
            platformType = PlatformType.IOS,
            osVersion = UIDevice.currentDevice.systemVersion
        )
}

actual fun getPlatform(): Platform = IOSPlatform()