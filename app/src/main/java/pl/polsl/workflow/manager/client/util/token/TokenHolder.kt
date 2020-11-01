package pl.polsl.workflow.manager.client.util.token

import pl.polsl.workflow.manager.client.model.data.Authentication

interface TokenHolder {

    var token: Authentication?

}