package pl.polsl.workflow.manager.client.model.data

import android.location.Location
import java.time.Instant

val Authentication.checkedToken: String?
    get() =  if(expirationDate > Instant.now()) token else null

fun LatLng.getDistance(latLng: LatLng): Double {
    val result = FloatArray(1)
    Location.distanceBetween(latLng.latitude, latLng.longitude, latitude, longitude, result)
    return result[0].toDouble()
}

fun Localization.checkDistance(latLng: LatLng): Boolean {
    return true
    return this.latLng.getDistance(latLng) <= radius
}

