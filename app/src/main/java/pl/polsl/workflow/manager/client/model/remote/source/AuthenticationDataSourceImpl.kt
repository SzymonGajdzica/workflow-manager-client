package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.api.AuthenticationApi
import pl.polsl.workflow.manager.client.model.remote.data.AuthenticationApiModel

class AuthenticationDataSourceImpl(
        private val authenticationApi: AuthenticationApi
): AuthenticationDataSource {
    override suspend fun getAuthenticationToken(username: String, password: String): AuthenticationApiModel {
        return authenticationApi.getAuthenticationToken(username, password)
    }
}