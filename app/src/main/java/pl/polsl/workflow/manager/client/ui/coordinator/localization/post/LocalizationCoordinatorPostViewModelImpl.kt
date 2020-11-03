package pl.polsl.workflow.manager.client.ui.coordinator.localization.post

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.LatLng
import pl.polsl.workflow.manager.client.model.data.LocalizationPost
import pl.polsl.workflow.manager.client.model.repository.LocalizationRepository
import pl.polsl.workflow.manager.client.util.validator.InputValidator
import javax.inject.Inject

class LocalizationCoordinatorPostViewModelImpl @Inject constructor(
        application: Application,
        private val localizationRepository: LocalizationRepository,
        private val inputValidator: InputValidator
): LocalizationCoordinatorPostViewModel(application) {

    override val nameInputError: MutableLiveData<String> = MutableLiveData()
    override val selectedLatLng: MutableLiveData<LatLng> = MutableLiveData()
    override val selectedRadius: MutableLiveData<Double> = MutableLiveData(20.0)

    override fun onLatLngSelected(latLng: LatLng) {
        selectedLatLng.value = latLng
    }

    override fun onRadiusSelected(radius: Double) {
        selectedRadius.value = radius
    }

    override fun createLocalization(localizationPost: LocalizationPost) = launchWithLoader {
        nameInputError.value = inputValidator.validateBlankText(localizationPost.name)
        if(nameInputError.value == null) {
            when(val result = localizationRepository.addLocalization(localizationPost)) {
                is RepositoryResult.Success -> {
                    showSuccessMessage(getString(R.string.localizationCreated))
                    finishFragment()
                }
                is RepositoryResult.Error -> showErrorMessage(result.error)
            }
        }
    }

}