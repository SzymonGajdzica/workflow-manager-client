package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.UserView
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.mapper.map
import pl.polsl.workflow.manager.client.model.remote.toRepositoryResult

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getSelf(): RepositoryResult<UserView> {
        return runCatching {
            userApi.getSelf().map()
        }.toRepositoryResult()
    }

    override suspend fun getAllUsers(): RepositoryResult<List<UserView>> {
        return runCatching {
            userApi.getAllUsers().map { it.map() }
        }.toRepositoryResult()
    }
}