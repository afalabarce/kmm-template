package io.afalabarce.template.kmm.models.features.example.local

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CacheExampleEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String
)