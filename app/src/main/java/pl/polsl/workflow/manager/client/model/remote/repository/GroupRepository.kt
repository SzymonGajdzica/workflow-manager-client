package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.GroupView
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult

interface GroupRepository {

    suspend fun getWorkerGroup(): RepositoryResult<GroupView>

}