package pl.polsl.workflow.manager.client.model.data

import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
        override val id: Long,
        val username: String,
        val role: Int,
        val enabled: Boolean,
): Identifiable