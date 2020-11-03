package pl.polsl.workflow.manager.client.ui.coordinator.localization

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class LocalizationCoordinatorViewModel(application: Application): BaseViewModel(application) {

    abstract val localizations: LiveData<List<Localization>>

}