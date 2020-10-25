package pl.polsl.workflow.manager.client.utils

import pl.polsl.workflow.manager.client.model.data.Authentication

interface TokenHolder {

    var token: Authentication?

}