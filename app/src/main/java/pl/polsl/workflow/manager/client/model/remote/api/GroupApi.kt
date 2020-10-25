package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModel
import retrofit2.http.GET

interface GroupApi {

    @GET("group/worker/")
    suspend fun getWorkerGroup(): GroupApiModel

}