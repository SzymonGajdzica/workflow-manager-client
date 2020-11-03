package pl.polsl.workflow.manager.client.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentMapSelectBinding
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.shared.SharedViewModelImpl

class MapSelectFragment: BaseFragment<MapSelectViewModel>(), OnMapReadyCallback {

    private lateinit var viewDataBinding: FragmentMapSelectBinding

    override val viewModel: MapSelectViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    private val markerLocalizationMap: HashMap<Marker, Localization> = hashMapOf()
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentMapSelectBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            sharedViewModel = createSharedViewModel<SharedViewModelImpl>()
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapSelectMapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun setupObservables(viewModel: MapSelectViewModel) {
        super.setupObservables(viewModel)
        viewModel.localizations.observe {
            tryToFillMap(it)
        }
    }

    private fun tryToFillMap(localizations: List<Localization>?) {
        val googleMap = googleMap ?: return
        val context = context ?: return
        localizations ?: return
        googleMap.clear()
        markerLocalizationMap.clear()
        localizations.forEach { localization ->
            markerLocalizationMap[googleMap.addLocalization(context, localization).first] = localization
        }
        googleMap.zoom(localizations.map { it.latLng }, true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val context = context ?: return
        googleMap.baseSetup(context)
        googleMap.setOnInfoWindowClickListener {
            viewDataBinding.sharedViewModel?.selectLocalization(markerLocalizationMap.getValue(it))
            findNavController().navigateUp()
        }
        tryToFillMap(viewModel.localizations.value)
    }

}