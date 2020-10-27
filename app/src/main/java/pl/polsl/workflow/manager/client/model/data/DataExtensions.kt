package pl.polsl.workflow.manager.client.model.data

import android.location.Location
import pl.polsl.workflow.manager.client.model.Identifiable
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

fun <T : Identifiable>Collection<T>.toMap(): Map<Long, T> {
    return map { it.id to it }.toMap()
}

val User.destinationActivityClass: Class<*>
    get() = when(role) {
        Role.WORKER -> WorkerActivity::class.java
        Role.COORDINATOR -> TODO()
        Role.MANAGER -> ManagerActivity::class.java
        else -> throw IllegalArgumentException()
    }

val Task.state: Int
    get() = when {
        taskManagerReport != null -> TaskState.ACCEPTED
        taskWorkerReport != null -> TaskState.FINISHED
        startDate != null -> TaskState.STARTED
        else -> TaskState.CREATED
    }