package pl.polsl.workflow.manager.client.model.repository

import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Authentication
import pl.polsl.workflow.manager.client.model.mapper.map
import pl.polsl.workflow.manager.client.model.remote.source.AuthenticationDataSource
import pl.polsl.workflow.manager.client.model.safeCall

class AuthenticationRepositoryImpl(
    private val authenticationDataSource: AuthenticationDataSource
): AuthenticationRepository {

    override suspend fun getAuthenticationToken(
        username: String,
        password: String
    ): RepositoryResult<Authentication> = safeCall {
        authenticationDataSource.getAuthenticationToken(username, password).map()
    }

}