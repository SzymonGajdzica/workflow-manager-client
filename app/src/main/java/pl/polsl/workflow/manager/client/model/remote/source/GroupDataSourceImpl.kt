package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.api.GroupApi
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModel
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModelPatch
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModelPost
import pl.polsl.workflow.manager.client.util.lazy.list.ExpirableCachedLazyList
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList
import java.time.Instant

class GroupDataSourceImpl(
        private val groupApi: GroupApi
): GroupDataSource {

    private val workerLazyList: LazyList<GroupApiModel> = ExpirableCachedLazyList(Instant.ofEpochSecond(30L * 60L)) {
        listOf(groupApi.getWorkerGroup())
    }

    private val lazyList: LazyList<GroupApiModel> = ExpirableCachedLazyList(Instant.ofEpochSecond(30L * 60L)) {
        groupApi.getAllGroups()
    }

    override fun getWorkerGroup(): LazyList<GroupApiModel> {
        return workerLazyList
    }

    override fun getAllGroups(): LazyList<GroupApiModel> {
        return lazyList
    }

    override suspend fun createGroup(groupApiModelPost: GroupApiModelPost): GroupApiModel {
        return groupApi.createGroup(groupApiModelPost).also {
            lazyList.supplyItem(it)
        }
    }

    override suspend fun updateGroup(groupId: Long, groupApiModelPatch: GroupApiModelPatch): GroupApiModel {
        return groupApi.updateGroup(groupId, groupApiModelPatch).also {
            lazyList.clear()
        }
    }
}