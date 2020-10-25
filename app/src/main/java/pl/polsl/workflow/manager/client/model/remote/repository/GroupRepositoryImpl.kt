package pl.polsl.workflow.manager.client.model.remote.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.api.GroupApi
import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.mapper.map
import pl.polsl.workflow.manager.client.model.remote.safeCall

class GroupRepositoryImpl(
    private val userApi: UserApi,
    private val groupApi: GroupApi
): GroupRepository {

    override suspend fun getWorkerGroup(): RepositoryResult<Group> = safeCall {
        val usersPending = GlobalScope.async { userApi.getAllUsers() }
        val groupPending = GlobalScope.async { groupApi.getWorkerGroup() }
        groupPending.await().map(usersPending.await().map { it.id to it }.toMap())
    }

}