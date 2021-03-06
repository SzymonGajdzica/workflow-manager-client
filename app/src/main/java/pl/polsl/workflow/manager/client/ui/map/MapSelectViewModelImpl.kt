package pl.polsl.workflow.manager.client.ui.map

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.model.repository.LocalizationRepository
import javax.inject.Inject

class MapSelectViewModelImpl @Inject constructor(
    application: Application,
    private val locationRepository: LocalizationRepository
): MapSelectViewModel(application) {

    override val localizations: MutableLiveData<List<Localization>> = MutableLiveData(null)

    private fun loadLocalizations() = launchWithLoader {
        localizations.value = null
        when (val result = locationRepository.getAllLocalizations()) {
            is RepositoryResult.Success -> localizations.value = result.data
            is RepositoryResult.Error -> showError(result.error)
        }
    }


    override fun reloadData() {
        super.reloadData()
        loadLocalizations()
    }

}