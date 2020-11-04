package pl.polsl.workflow.manager.client.ui.map

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.LatLng
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.util.extension.hasLocationPermission
import pl.polsl.workflow.manager.client.util.extension.isDarkMode
import pl.polsl.workflow.manager.client.util.extension.mGetColor

fun LatLng.toGoogleLatLng(): com.google.android.gms.maps.model.LatLng {
    return com.google.android.gms.maps.model.LatLng(latitude, longitude)
}

fun com.google.android.gms.maps.model.LatLng.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}

fun GoogleMap.addLocalization(context: Context, localization: Localization): Pair<Marker, Circle> {
    val marker = addMarker(
        MarkerOptions()
            .position(localization.latLng.toGoogleLatLng())
            .title(localization.name)
    )
    val circle = addCircle(
        CircleOptions()
            .center(localization.latLng.toGoogleLatLng())
            .radius(localization.radius)
            .fillColor(context.mGetColor(R.color.mapCircleBackground)))
    return Pair(marker, circle)
}

@SuppressLint("MissingPermission")
fun GoogleMap.baseSetup(context: Context?) {
    uiSettings.apply {
        isCompassEnabled = true
        isMyLocationButtonEnabled = true
        isMapToolbarEnabled = true
    }
    if(context?.isDarkMode == true)
        setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_dark_mode_style))
    if(context?.hasLocationPermission() == true)
        isMyLocationEnabled = true
}

fun GoogleMap.zoom(latLng: LatLng, animate: Boolean) {
    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng.toGoogleLatLng(), 18.0f)
    if(animate)
        animateCamera(cameraUpdate)
    else
        moveCamera(cameraUpdate)
}

fun GoogleMap.zoom(latLngCollection: Collection<LatLng>, animate: Boolean) {
    val builder = LatLngBounds.Builder()
    latLngCollection.forEach { latLng ->
        builder.include(latLng.toGoogleLatLng())
    }
    val cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), 100)
    if(animate)
        animateCamera(cameraUpdate)
    else
        moveCamera(cameraUpdate)
}