package pl.polsl.workflow.manager.client.util.location

import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.*
import pl.polsl.workflow.manager.client.model.data.LatLng
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationReaderImpl(
    private val context: Context
): LocationReader {

    private var locationUpdateCallback: ((LatLng?) -> Unit)? = null

    private val locationUpdate = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationUpdateCallback?.invoke(locationResult?.lastLocation?.let {
                LatLng(it.latitude, it.longitude)
            })
        }
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @RequiresPermission(value = android.Manifest.permission.ACCESS_FINE_LOCATION)
    override fun startLocationUpdates(locationUpdateCallback: ((LatLng?) -> Unit)?) {
        stopLocationUpdates()
        val request = LocationRequest().apply {
            interval = 0
            fastestInterval = 0
            smallestDisplacement = 0f
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(request, locationUpdate, context.mainLooper)
        this.locationUpdateCallback = locationUpdateCallback
    }

    override fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationUpdate)
        locationUpdateCallback = null
    }

    @RequiresPermission(value = android.Manifest.permission.ACCESS_FINE_LOCATION)
    override suspend fun getLastLatLng(): LatLng? = suspendCoroutine { cont ->
        fusedLocationClient.lastLocation.addOnCompleteListener {
            if(it.isSuccessful) {
                val result = it.result
                cont.resume(LatLng(result.latitude, result.longitude))
            } else {
                cont.resume(null)
            }
        }
    }
}