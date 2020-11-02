package pl.polsl.workflow.manager.client.model.data

import android.location.Location
import pl.polsl.workflow.manager.client.model.remote.data.IdentifiableApiModel
import pl.polsl.workflow.manager.client.ui.coordinator.CoordinatorActivity
import pl.polsl.workflow.manager.client.ui.manager.ManagerActivity
import pl.polsl.workflow.manager.client.ui.worker.WorkerActivity
import java.time.Instant

val Authentication.checkedToken: String?
    get() =  if(expirationDate > Instant.now()) token else null

fun LatLng.getDistance(latLng: LatLng): Double {
    val result = FloatArray(1)
    Location.distanceBetween(latLng.latitude, latLng.longitude, latitude, longitude, result)
    return result[0].toDouble()
}

fun Localization.checkDistance(latLng: LatLng): Boolean {
    return true // todo
    return this.latLng.getDistance(latLng) <= radius
}

fun <T : IdentifiableApiModel>Collection<T>.toMap(): Map<Long, T> {
    return map { it.id to it }.toMap()
}

val User.destinationActivityClass: Class<*>
    get() = when(role) {
        Role.WORKER -> WorkerActivity::class.java
        Role.COORDINATOR -> CoordinatorActivity::class.java
        Role.MANAGER -> ManagerActivity::class.java
        else -> throw IllegalArgumentException()
    }

val Task.status: Int
    get() = when {
        taskManagerReport != null -> TaskStatus.ACCEPTED
        taskWorkerReport != null -> TaskStatus.FINISHED
        startDate != null -> TaskStatus.STARTED
        else -> TaskStatus.CREATED
    }

fun Task.getSuperTask(tasks: List<Task>): AllowableValue<Task>? {
    val superTask = tasks.find { it.taskManagerReport?.fixTask?.id == id }
    return when {
        superTask == null && isSubTask -> AllowableValue.NotAllowed(null)
        superTask != null -> AllowableValue.Allowed(superTask)
        else -> null
    }
}