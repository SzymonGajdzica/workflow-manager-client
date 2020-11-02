package pl.polsl.workflow.manager.client.model.repository

import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.GroupPatch
import pl.polsl.workflow.manager.client.model.data.GroupPost
import pl.polsl.workflow.manager.client.model.mapper.map
import pl.polsl.workflow.manager.client.model.remote.source.GroupDataSource
import pl.polsl.workflow.manager.client.model.remote.source.UserDataSource
import pl.polsl.workflow.manager.client.model.safeCall

class GroupRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val groupDataSource: GroupDataSource
): GroupRepository {

    override suspend fun getWorkerGroup(): RepositoryResult<Group> = safeCall {
        groupDataSource.getWorkerGroup().getAll().first().map(userDataSource.getAllUsers())
    }

    override suspend fun getAllGroups(): RepositoryResult<List<Group>> = safeCall {
        groupDataSource.getAllGroups().getAll().map { it.map(userDataSource.getAllUsers()) }.sortedBy { it.name }
    }

    override suspend fun createGroup(groupPost: GroupPost): RepositoryResult<Group> = safeCall {
        groupDataSource.createGroup(groupPost.map()).map(userDataSource.getAllUsers())
    }

    override suspend fun updateGroup(group: Group, groupPatch: GroupPatch): RepositoryResult<Group> = safeCall {
        groupDataSource.updateGroup(group.id, groupPatch.map()).map(userDataSource.getAllUsers())
    }

}