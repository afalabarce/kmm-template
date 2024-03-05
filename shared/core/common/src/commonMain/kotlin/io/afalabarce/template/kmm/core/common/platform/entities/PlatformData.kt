package io.afalabarce.template.kmm.core.common.platform.entities

import io.afalabarce.template.kmm.core.common.platform.enums.PlatformType

data class PlatformData(
    val platformType: PlatformType,
    val osVersion: String
) {
    override fun toString(): String = "${platformType.name} v. $osVersion"
}