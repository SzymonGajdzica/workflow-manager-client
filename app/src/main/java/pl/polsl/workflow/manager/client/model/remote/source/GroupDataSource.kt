package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModel
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModelPatch
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModelPost
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList

interface GroupDataSource {

    fun getWorkerGroup(): LazyList<GroupApiModel>

    fun getAllGroups(): LazyList<GroupApiModel>

    suspend fun createGroup(groupApiModelPost: GroupApiModelPost): GroupApiModel

    suspend fun updateGroup(groupId: Long, groupApiModelPatch: GroupApiModelPatch): GroupApiModel

}