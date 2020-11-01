package pl.polsl.workflow.manager.client.ui.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.LatLng
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.util.extension.mGetColor

fun LatLng.toGoogleLatLng(): com.google.android.gms.maps.model.LatLng {
    return com.google.android.gms.maps.model.LatLng(latitude, longitude)
}

fun GoogleMap.addLocalization(context: Context, localization: Localization): Marker {
    val marker = addMarker(
        MarkerOptions()
            .position(localization.latLng.toGoogleLatLng())
            .title(localization.name)
    )
    addCircle(
        CircleOptions()
            .center(localization.latLng.toGoogleLatLng())
            .radius(localization.radius)
            .fillColor(context.mGetColor(R.color.mapCircleBackground)))
    return marker
}