package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.data.UserPost
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.mapper.map
import pl.polsl.workflow.manager.client.model.remote.safeCall

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getSelf(): RepositoryResult<User> = safeCall {
        userApi.getSelf().map()
    }

    override suspend fun getAllUsers(): RepositoryResult<List<User>> = safeCall {
        userApi.getAllUsers().map { it.map() }
    }

    override suspend fun createUser(userPost: UserPost): RepositoryResult<User> = safeCall {
        userApi.createUser(userPost.map()).map()
    }

}