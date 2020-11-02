package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.LocalizationApiModel
import pl.polsl.workflow.manager.client.model.remote.data.LocalizationApiModelPost
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LocalizationApi {

    @GET("localization/")
    suspend fun getAllLocalizations(): List<LocalizationApiModel>

    @POST("localization/")
    suspend fun addLocalization(@Body localizationApiModelPost: LocalizationApiModelPost): LocalizationApiModel

}