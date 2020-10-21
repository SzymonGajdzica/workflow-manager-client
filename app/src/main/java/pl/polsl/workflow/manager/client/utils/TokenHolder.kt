package pl.polsl.workflow.manager.client.utils

import pl.polsl.workflow.manager.client.model.data.AuthenticationView

interface TokenHolder {

    var token: AuthenticationView?

}