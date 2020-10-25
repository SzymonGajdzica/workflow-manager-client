package pl.polsl.workflow.manager.client.model.data

data class GroupPatch(
    val name: String?,
    val manager: User?,
    val workers: List<User>?,
)
