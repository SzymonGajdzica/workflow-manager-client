package pl.polsl.workflow.manager.client.model.remote.mapper

import pl.polsl.workflow.manager.client.model.data.AuthenticationView
import pl.polsl.workflow.manager.client.model.remote.data.AuthenticationApiView

fun AuthenticationApiView.map(): AuthenticationView {
    return AuthenticationView(
        token = token,
        expirationDate = expirationDate
    )
}