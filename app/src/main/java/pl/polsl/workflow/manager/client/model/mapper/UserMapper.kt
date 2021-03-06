package pl.polsl.workflow.manager.client.model.mapper

import pl.polsl.workflow.manager.client.model.data.Role
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.data.UserPatch
import pl.polsl.workflow.manager.client.model.data.UserPost
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModel
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModelPatch
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModelPost

fun UserApiModel.map(): User {
    return User(
        id = id,
        username = username,
        role = Role.fromString(role),
        enabled = enabled
    )
}

fun UserPost.map(): UserApiModelPost {
    return UserApiModelPost(
            username = username,
            role = Role.toString(role),
            password = password
    )
}

fun UserPatch.map(): UserApiModelPatch {
    return UserApiModelPatch(
            enabled = enabled
    )
}