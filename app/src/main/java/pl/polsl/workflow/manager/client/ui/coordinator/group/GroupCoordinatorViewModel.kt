package pl.polsl.workflow.manager.client.ui.coordinator.group

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class GroupCoordinatorViewModel(application: Application): BaseViewModel(application) {

    abstract val groups: LiveData<List<Group>>

}