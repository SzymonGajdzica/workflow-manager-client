package pl.polsl.workflow.manager.client.model.data

import java.io.Serializable
import java.util.*

data class AuthenticationView(
    val token: String,
    val expirationDate: Date,
): Serializable