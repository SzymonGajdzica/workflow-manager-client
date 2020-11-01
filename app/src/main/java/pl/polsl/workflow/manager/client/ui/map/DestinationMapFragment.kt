package pl.polsl.workflow.manager.client.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.util.extension.getParcelable
import pl.polsl.workflow.manager.client.util.extension.hasPermission

class DestinationMapFragment : Fragment(), OnMapReadyCallback {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_destination_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.destinationMapMapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        val destinationLocalization: Localization? = arguments?.getParcelable()
        val context = context
        if(context == null || destinationLocalization == null)
            return
        googleMap.uiSettings.apply {
            isCompassEnabled = true
            isMyLocationButtonEnabled = true
            isMapToolbarEnabled = true
        }
        if(context.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            googleMap.isMyLocationEnabled = true
        googleMap.addLocalization(context, destinationLocalization).showInfoWindow()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLocalization.latLng.toGoogleLatLng(), 18.0f))
    }
}