package pl.polsl.workflow.manager.client.model.repository

import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.data.UserPost
import pl.polsl.workflow.manager.client.model.mapper.map
import pl.polsl.workflow.manager.client.model.remote.source.UserDataSource
import pl.polsl.workflow.manager.client.model.safeCall

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun getSelf(): RepositoryResult<User> = safeCall {
        userDataSource.getSelf().map()
    }

    override suspend fun getAllUsers(): RepositoryResult<List<User>> = safeCall {
        userDataSource.getAllUsers().getAll().map { it.map() }.sortedBy { it.username }
    }

    override suspend fun createUser(userPost: UserPost): RepositoryResult<User> = safeCall {
        userDataSource.createUser(userPost.map()).map()
    }

}