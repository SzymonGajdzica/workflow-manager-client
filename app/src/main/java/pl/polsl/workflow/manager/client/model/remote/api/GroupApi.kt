package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModel
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModelPatch
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModelPost
import retrofit2.http.*

interface GroupApi {

    @GET("group/worker/")
    suspend fun getWorkerGroup(): GroupApiModel

    @GET("group/all/")
    suspend fun getAllGroups(): List<GroupApiModel>

    @POST("group/")
    suspend fun createGroup(@Body groupApiModelPost: GroupApiModelPost): GroupApiModel

    @PATCH("group/{groupId}/")
    suspend fun updateGroup(@Path("groupId") groupId: Long, @Body groupApiModelPatch: GroupApiModelPatch): GroupApiModel

}