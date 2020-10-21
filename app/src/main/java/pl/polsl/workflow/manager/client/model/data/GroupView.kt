package pl.polsl.workflow.manager.client.model.data

import java.io.Serializable

data class GroupView(
        val id: Long,
        val name: String,
        val manager: UserView,
        val workers: List<UserView>
): Serializable
