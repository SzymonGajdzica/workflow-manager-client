package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.UserApiModel
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModelPost
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("user/self/")
    suspend fun getSelf(): UserApiModel

    @GET("user/")
    suspend fun getAllUsers(): List<UserApiModel>

    @POST("user/")
    suspend fun createUser(userApiModelPost: UserApiModelPost): UserApiModel

}