package pl.polsl.workflow.manager.client.model.remote.mapper

import pl.polsl.workflow.manager.client.model.data.GroupView
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiView
import pl.polsl.workflow.manager.client.model.remote.data.UserApiView

fun GroupApiView.map(users: Map<Long, UserApiView>): GroupView {
    return GroupView(
        id = id,
        name = name,
        manager = users.getValue(managerId).map(),
        workers = workerIdList.mapNotNull { users[it]?.map() }
    )
}