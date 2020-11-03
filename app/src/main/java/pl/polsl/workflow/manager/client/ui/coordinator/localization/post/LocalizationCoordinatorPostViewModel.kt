package pl.polsl.workflow.manager.client.ui.coordinator.localization.post

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.LatLng
import pl.polsl.workflow.manager.client.model.data.LocalizationPost
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class LocalizationCoordinatorPostViewModel(application: Application): BaseViewModel(application) {

    abstract val nameInputError: LiveData<String>
    abstract val selectedLatLng: LiveData<LatLng>
    abstract val selectedRadius: LiveData<Double>

    abstract fun onRadiusSelected(radius: Double)
    abstract fun onLatLngSelected(latLng: LatLng)
    abstract fun createLocalization(localizationPost: LocalizationPost)

}