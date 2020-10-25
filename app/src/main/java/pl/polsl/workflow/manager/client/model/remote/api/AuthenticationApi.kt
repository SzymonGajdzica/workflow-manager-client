package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.AuthenticationApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthenticationApi {

    @GET("authentication/createAuthenticationToken/")
    suspend fun getAuthenticationToken(@Query("username") username: String, @Query("plainPassword") password: String): AuthenticationApiModel

}