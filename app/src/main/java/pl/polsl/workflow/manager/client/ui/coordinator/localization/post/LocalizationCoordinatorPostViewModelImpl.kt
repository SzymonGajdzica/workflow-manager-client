package pl.polsl.workflow.manager.client.ui.coordinator.localization.post

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.LatLng
import pl.polsl.workflow.manager.client.model.data.LocalizationPost
import pl.polsl.workflow.manager.client.model.repository.LocalizationRepository
import javax.inject.Inject

class LocalizationCoordinatorPostViewModelImpl @Inject constructor(
        application: Application,
        private val localizationRepository: LocalizationRepository
): LocalizationCoordinatorPostViewModel(application) {

    override val nameInputError: MutableLiveData<String> = MutableLiveData()
    override val selectedLatLng: MutableLiveData<LatLng> = MutableLiveData()

    override fun onLatLngSelected(latLng: LatLng) {
        selectedLatLng.value = latLng
    }

    override fun createLocalization(localizationPost: LocalizationPost) = launchWithLoader {
        when(val result = localizationRepository.getAllLocalizations()) {
            is RepositoryResult.Success -> {
                showSuccessMessage(getString(R.string.localizationCreated))
                finishFragment()
            }
            is RepositoryResult.Error -> showErrorMessage(result.error)
        }
    }

}