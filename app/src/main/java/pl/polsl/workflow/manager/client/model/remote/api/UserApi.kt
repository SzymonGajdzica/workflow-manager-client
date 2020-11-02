package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.UserApiModel
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModelPatch
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModelPost
import retrofit2.http.*

interface UserApi {

    @GET("user/self/")
    suspend fun getSelf(): UserApiModel

    @GET("user/")
    suspend fun getAllUsers(): List<UserApiModel>

    @POST("user/")
    suspend fun createUser(@Body userApiModelPost: UserApiModelPost): UserApiModel

    @PATCH("user/{userId}/")
    suspend fun updateUser(@Path("userId") userId: Long, @Body userApiModelPatch: UserApiModelPatch): UserApiModel

}