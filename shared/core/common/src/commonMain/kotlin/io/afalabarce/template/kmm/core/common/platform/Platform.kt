package io.afalabarce.template.kmm.core.common.platform

import io.afalabarce.template.kmm.core.common.platform.entities.PlatformData

interface Platform {
    val platformData: PlatformData
}

expect fun getPlatform(): Platform