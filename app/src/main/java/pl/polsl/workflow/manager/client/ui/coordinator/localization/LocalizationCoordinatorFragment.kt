package pl.polsl.workflow.manager.client.ui.coordinator.localization

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.android.synthetic.main.fragment_localizations_coordinator.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentLocalizationsCoordinatorBinding
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.map.addLocalization
import pl.polsl.workflow.manager.client.ui.map.toGoogleLatLng
import pl.polsl.workflow.manager.client.util.extension.hasPermission

class LocalizationCoordinatorFragment: BaseFragment<LocalizationCoordinatorViewModel>(), OnMapReadyCallback {

    private lateinit var viewDataBinding: FragmentLocalizationsCoordinatorBinding

    override val viewModel: LocalizationCoordinatorViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentLocalizationsCoordinatorBinding.inflate(inflater, container, false).apply {
                viewModel = createViewModel()
                lifecycleOwner = viewLifecycleOwner
            }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.appComponent.inject(this)
    }

    override fun setupViews(view: View) {
        super.setupViews(view)
        val mapFragment = childFragmentManager.findFragmentById(R.id.coordinatorLocalizationMapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun setupObservables(viewModel: LocalizationCoordinatorViewModel) {
        super.setupObservables(viewModel)
        viewModel.localizations.observe {
            tryToFillMap(it)
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.coordinatorLocalizationAddLocalization.setOnClickListener {
            findNavController().navigate(
                    R.id.action_navigation_localizations_coordinator_to_localizationCoordinatorPostFragment
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.uiSettings.apply {
            isCompassEnabled = true
            isMyLocationButtonEnabled = true
        }
        if(context?.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) == true)
            googleMap.isMyLocationEnabled = true
        tryToFillMap(viewModel.localizations.value)
    }

    private fun tryToFillMap(localizations: List<Localization>?) {
        val googleMap = googleMap ?: return
        val context = context ?: return
        localizations ?: return
        googleMap.clear()
        val builder = LatLngBounds.Builder()
        localizations.forEach { localization ->
            googleMap.addLocalization(context, localization)
            builder.include(localization.latLng.toGoogleLatLng())
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100))
    }

}