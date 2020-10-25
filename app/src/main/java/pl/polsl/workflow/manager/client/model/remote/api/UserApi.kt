package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.UserApiModel
import retrofit2.http.GET

interface UserApi {

    @GET("user/self/")
    suspend fun getSelf(): UserApiModel

    @GET("user/")
    suspend fun getAllUsers(): List<UserApiModel>

}