package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.Authentication
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult

interface AuthenticationRepository {

    suspend fun getAuthenticationToken(username: String, password: String): RepositoryResult<Authentication>

}