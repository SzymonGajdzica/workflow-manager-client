package pl.polsl.workflow.manager.client.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.util.extension.get

class DestinationMapFragment : BaseFragment(), OnMapReadyCallback {

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

    override fun onMapReady(googleMap: GoogleMap) {
        val destinationLocalization: Localization = arguments?.get() ?: return
        val context = context ?: return
        googleMap.baseSetup(context)
        googleMap.addLocalization(context, destinationLocalization).first.showInfoWindow()
        googleMap.zoom(destinationLocalization.latLng, false)
    }
}