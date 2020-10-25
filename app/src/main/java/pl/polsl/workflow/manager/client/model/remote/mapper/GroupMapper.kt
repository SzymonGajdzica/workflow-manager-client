package pl.polsl.workflow.manager.client.model.remote.mapper

import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModel
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModel

fun GroupApiModel.map(users: Map<Long, UserApiModel>): Group {
    return Group(
        id = id,
        name = name,
        manager = users.getValue(managerId).map(),
        workers = workerIdList.mapNotNull { users[it]?.map() }
    )
}