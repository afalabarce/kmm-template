package io.afalabarce.template.kmm.data.datasources.core.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST

interface ApiService {
    @GET("/api/values")
    suspend fun getAllItems(): List<String>

    @POST("/api/values")
    suspend fun putAllItems(@Body items: List<String>)

    companion object {
        const val API_URL = "https://your.own.api"
    }
}
