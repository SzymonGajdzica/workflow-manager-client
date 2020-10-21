package pl.polsl.workflow.manager.client.model.remote.mapper

import pl.polsl.workflow.manager.client.model.data.Role
import pl.polsl.workflow.manager.client.model.data.UserView
import pl.polsl.workflow.manager.client.model.remote.data.UserApiView

fun UserApiView.map(): UserView {
    return UserView(
        id = id,
        username = username,
        role = Role.fromString(role),
        enabled = enabled
    )
}