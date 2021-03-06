package pl.polsl.workflow.manager.client.model.data

import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    override val id: Long,
    val name: String,
    val manager: User?,
    val workers: List<User>
): Identifiable
