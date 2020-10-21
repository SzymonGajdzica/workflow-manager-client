package pl.polsl.workflow.manager.client.model.data

enum class Role {

    WORKER, MANAGER, COORDINATOR;

    companion object {
        fun fromString(roleString: String): Role {
            return when (roleString) {
                "WORKER" -> WORKER
                "MANAGER" -> MANAGER
                "COORDINATOR" -> COORDINATOR
                else -> throw IllegalArgumentException()
            }
        }
    }

}