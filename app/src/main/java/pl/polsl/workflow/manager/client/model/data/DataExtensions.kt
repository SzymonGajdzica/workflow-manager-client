package pl.polsl.workflow.manager.client.model.data

import java.util.*

val AuthenticationView.checkedToken: String?
    get() =  if(expirationDate > Date()) token else null



