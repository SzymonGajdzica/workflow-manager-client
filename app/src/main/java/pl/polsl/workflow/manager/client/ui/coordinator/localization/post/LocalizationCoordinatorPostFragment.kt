package pl.polsl.workflow.manager.client.ui.coordinator.localization.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import kotlinx.android.synthetic.main.fragment_localizations_coordinator_post.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentLocalizationsCoordinatorPostBinding
import pl.polsl.workflow.manager.client.model.data.LatLng
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.model.data.LocalizationPost
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.map.addLocalization
import pl.polsl.workflow.manager.client.ui.map.baseSetup
import pl.polsl.workflow.manager.client.ui.map.toGoogleLatLng
import pl.polsl.workflow.manager.client.ui.map.toLatLng
import pl.polsl.workflow.manager.client.util.extension.safeValue

class LocalizationCoordinatorPostFragment: BaseFragment<LocalizationCoordinatorPostViewModel>(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private var circle: Circle? = null
    private lateinit var viewDataBinding: FragmentLocalizationsCoordinatorPostBinding

    override val viewModel: LocalizationCoordinatorPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
                FragmentLocalizationsCoordinatorPostBinding.inflate(inflater, container, false).apply {
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.coordinatorLocalizationPostMapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        view.coordinatorLocalizationPostRadiusSlider.value = viewModel.selectedRadius.safeValue.toFloat()
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.coordinatorLocalizationPostCreateButton.setOnClickListener {
            val latLng = viewModel.selectedLatLng.value
                    ?: return@setOnClickListener showErrorMessage(getString(R.string.selectLocalization))
            viewModel.createLocalization(LocalizationPost(
                    name = view.coordinatorLocalizationPostName.text.toString(),
                    latLng = latLng,
                    radius = viewModel.selectedRadius.safeValue
            ))
        }
        view.coordinatorLocalizationPostRadiusSlider.addOnChangeListener { _, value, _ ->
            viewModel.onRadiusSelected(value.toDouble())
        }
    }

    override fun setupObservables(viewModel: LocalizationCoordinatorPostViewModel) {
        super.setupObservables(viewModel)
        viewModel.nameInputError.observe { inputError ->
            view?.coordinatorLocalizationPostNameContainer?.error = inputError
        }
        viewModel.selectedLatLng.observe { latLng ->
            updateMarkerPosition(latLng)
        }
        viewModel.selectedRadius.safeObserve { radius ->
            circle?.radius = radius
        }
    }

    private fun updateMarkerPosition(latLng: LatLng?) {
        googleMap?.clear()
        circle = null
        latLng ?: return
        val context = context ?: return
        val radius = viewModel.selectedRadius.safeValue
        val localization = Localization(
                id = 0,
                name = getString(R.string.newLocalization),
                latLng = latLng,
                radius = radius
        )
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng.toGoogleLatLng(), 18.0f))
        circle = googleMap?.addLocalization(context, localization)?.second
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.baseSetup(context)
        googleMap.setOnMapClickListener {
            viewModel.onLatLngSelected(it.toLatLng())
        }
        updateMarkerPosition(viewModel.selectedLatLng.value)
    }




}