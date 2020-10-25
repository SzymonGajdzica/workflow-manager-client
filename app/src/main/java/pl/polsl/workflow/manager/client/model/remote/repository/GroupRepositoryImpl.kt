package pl.polsl.workflow.manager.client.model.remote.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.GroupPatch
import pl.polsl.workflow.manager.client.model.data.GroupPost
import pl.polsl.workflow.manager.client.model.data.toMap
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
        groupApi.getWorkerGroup().map(usersPending.await().toMap())
    }

    override suspend fun getAllGroups(): RepositoryResult<List<Group>> = safeCall {
        val usersPending = GlobalScope.async { userApi.getAllUsers() }
        val groupsPending = GlobalScope.async { groupApi.getAllGroups() }
        val usersMap = usersPending.await().toMap()
        groupsPending.await().map { it.map(usersMap) }
    }

    override suspend fun createGroup(groupPost: GroupPost): RepositoryResult<Group> = safeCall {
        groupApi.createGroup(groupPost.map()).map(mapOf())
    }

    override suspend fun updateGroup(group: Group, groupPatch: GroupPatch): RepositoryResult<Group> = safeCall {
        val usersPending = GlobalScope.async { userApi.getAllUsers() }
        groupApi.updateGroup(group.id, groupPatch.map()).map(usersPending.await().toMap())
    }

}