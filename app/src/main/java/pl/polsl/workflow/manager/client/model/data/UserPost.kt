package pl.polsl.workflow.manager.client.model.data

data class UserPost(
        val username: String,
        val password: String,
        val role: Int,
)