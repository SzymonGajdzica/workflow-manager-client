package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.Authentication
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.api.AuthenticationApi
import pl.polsl.workflow.manager.client.model.remote.mapper.map
import pl.polsl.workflow.manager.client.model.remote.safeCall

class AuthenticationRepositoryImpl(
    private val authenticationApi: AuthenticationApi
): AuthenticationRepository {

    override suspend fun getAuthenticationToken(
        username: String,
        password: String
    ): RepositoryResult<Authentication> = safeCall {
        authenticationApi.getAuthenticationToken(username, password).map()
    }

}