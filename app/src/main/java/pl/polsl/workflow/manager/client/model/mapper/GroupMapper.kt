package pl.polsl.workflow.manager.client.model.mapper

import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.GroupPatch
import pl.polsl.workflow.manager.client.model.data.GroupPost
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModel
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModelPatch
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModelPost
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModel
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList

suspend fun GroupApiModel.map(users: LazyList<UserApiModel>): Group {
    return Group(
        id = id,
        name = name,
        manager = users.getItem(managerId).map(),
        workers = workerIdList.map { users.getItem(it).map() }
    )
}

fun GroupPost.map(): GroupApiModelPost {
    return GroupApiModelPost(
            name = name,
            managerId = manager.id
    )
}

fun GroupPatch.map(): GroupApiModelPatch {
    return GroupApiModelPatch(
            name = name,
            managerId = manager?.id,
            workerIds = workers?.map { it.id }
    )
}