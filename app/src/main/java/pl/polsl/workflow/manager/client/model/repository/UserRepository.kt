package pl.polsl.workflow.manager.client.model.repository

import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.data.UserPatch
import pl.polsl.workflow.manager.client.model.data.UserPost

interface UserRepository {

    suspend fun getSelf(): RepositoryResult<User>

    suspend fun getAllUsers(): RepositoryResult<List<User>>

    suspend fun createUser(userPost: UserPost): RepositoryResult<User>

    suspend fun updateUser(user: User, userPatch: UserPatch): RepositoryResult<User>

}