package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.data.AuthenticationApiModel

interface AuthenticationDataSource {

    suspend fun getAuthenticationToken(username: String, password: String): AuthenticationApiModel

}