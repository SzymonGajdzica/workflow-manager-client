package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.GroupPatch
import pl.polsl.workflow.manager.client.model.data.GroupPost
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult

interface GroupRepository {

    suspend fun getWorkerGroup(): RepositoryResult<Group>

    suspend fun getAllGroups(): RepositoryResult<List<Group>>

    suspend fun createGroup(groupPost: GroupPost): RepositoryResult<Group>

    suspend fun updateGroup(group: Group, groupPatch: GroupPatch): RepositoryResult<Group>

}