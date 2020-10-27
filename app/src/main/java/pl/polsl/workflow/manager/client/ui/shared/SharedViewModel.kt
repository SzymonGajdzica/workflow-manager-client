package pl.polsl.workflow.manager.client.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pl.polsl.workflow.manager.client.model.data.Localization

abstract class SharedViewModel: ViewModel() {

    abstract val localization: LiveData<Localization>

    abstract fun selectLocalization(localization: Localization?)

}