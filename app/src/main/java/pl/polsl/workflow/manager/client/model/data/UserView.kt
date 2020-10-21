package pl.polsl.workflow.manager.client.model.data

import java.io.Serializable

data class UserView(
    val id: Long,
    val username: String,
    val role: Role,
    val enabled: Boolean,
): Serializable