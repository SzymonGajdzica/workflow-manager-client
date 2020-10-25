package pl.polsl.workflow.manager.client.model.data

object Role {

    const val WORKER = 0
    const val MANAGER = 1
    const val COORDINATOR = 2

    fun fromString(roleString: String): Int {
        return when (roleString) {
            "WORKER" -> WORKER
            "MANAGER" -> MANAGER
            "COORDINATOR" -> COORDINATOR
            else -> throw IllegalArgumentException()
        }
    }


}