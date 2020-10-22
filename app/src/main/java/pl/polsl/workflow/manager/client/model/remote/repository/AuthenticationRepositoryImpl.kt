package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.AuthenticationView
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.api.AuthenticationApi
import pl.polsl.workflow.manager.client.model.remote.mapper.map
import pl.polsl.workflow.manager.client.model.remote.toRepositoryResult
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationApi: AuthenticationApi
): AuthenticationRepository {

    override suspend fun getAuthenticationToken(
        username: String,
        password: String
    ): RepositoryResult<AuthenticationView> {
        return runCatching {
            authenticationApi.getAuthenticationToken(username, password).map()
        }.toRepositoryResult()
    }

}