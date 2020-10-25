package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult

interface UserRepository {

    suspend fun getSelf(): RepositoryResult<User>

    suspend fun getAllUsers(): RepositoryResult<List<User>>

}