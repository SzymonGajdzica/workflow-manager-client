package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.UserApiView
import retrofit2.http.GET

interface UserApi {

    @GET("user/self/")
    suspend fun getSelf(): UserApiView

    @GET("user/")
    suspend fun getAllUsers(): List<UserApiView>

}