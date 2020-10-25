package pl.polsl.workflow.manager.client.ui.worker.task

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.MarkerOptions
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.hasPermission
import pl.polsl.workflow.manager.client.mGetColor
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.toGoogleLatLng

class DestinationMapsFragment : Fragment() {

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        val destinationLocalization: Localization? = arguments?.getParcelable("localization")
        val context = context
        if(context == null || destinationLocalization == null)
            return@OnMapReadyCallback
        googleMap.uiSettings.apply {
            isCompassEnabled = true
            isMyLocationButtonEnabled = true
            isMapToolbarEnabled = true
        }
        if(context.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            googleMap.isMyLocationEnabled = true
        googleMap.addMarker(
            MarkerOptions()
                .position(destinationLocalization.latLng.toGoogleLatLng())
                .title(destinationLocalization.name)
        ).showInfoWindow()
        googleMap.addCircle(
                CircleOptions()
                        .center(destinationLocalization.latLng.toGoogleLatLng())
                        .radius(destinationLocalization.radius)
                        .fillColor(context.mGetColor(R.color.listBackgroundColor)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLocalization.latLng.toGoogleLatLng(), 18.0f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapsFragmentMap) as? SupportMapFragment
        mapFragment?.getMapAsync(callback)
    }
}