package pl.polsl.workflow.manager.client.utils

import androidx.annotation.RequiresPermission
import pl.polsl.workflow.manager.client.model.data.LatLng

interface LocationReader {

    @RequiresPermission(value = android.Manifest.permission.ACCESS_FINE_LOCATION)
    fun startLocationUpdates(locationUpdateCallback: ((LatLng?) -> Unit)? = null)

    fun stopLocationUpdates()

    @RequiresPermission(value = android.Manifest.permission.ACCESS_FINE_LOCATION)
    suspend fun getLastLatLng(): LatLng?

}