package pl.polsl.workflow.manager.client.ui.shared

import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.data.Localization

class SharedViewModelImpl: SharedViewModel() {

    override val localization: MutableLiveData<Localization> = MutableLiveData(null)

    override fun selectLocalization(localization: Localization) {
        this.localization.value = localization
    }

}