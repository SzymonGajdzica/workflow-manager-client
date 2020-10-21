package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.UserView
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult

interface UserRepository {

    suspend fun getSelf(): RepositoryResult<UserView>

    suspend fun getAllUsers(): RepositoryResult<List<UserView>>

}