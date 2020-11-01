package pl.polsl.workflow.manager.client.model.repository

import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Authentication

interface AuthenticationRepository {

    suspend fun getAuthenticationToken(username: String, password: String): RepositoryResult<Authentication>

}