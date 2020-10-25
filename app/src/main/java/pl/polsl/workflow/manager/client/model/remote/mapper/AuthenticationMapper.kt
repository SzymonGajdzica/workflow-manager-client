package pl.polsl.workflow.manager.client.model.remote.mapper

import pl.polsl.workflow.manager.client.model.data.Authentication
import pl.polsl.workflow.manager.client.model.remote.data.AuthenticationApiModel

fun AuthenticationApiModel.map(): Authentication {
    return Authentication(
        token = token,
        expirationDate = expirationDate
    )
}