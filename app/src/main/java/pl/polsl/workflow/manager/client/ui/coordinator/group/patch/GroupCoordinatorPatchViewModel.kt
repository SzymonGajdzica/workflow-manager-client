package pl.polsl.workflow.manager.client.ui.coordinator.group.patch

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.GroupPatch
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class GroupCoordinatorPatchViewModel(application: Application): BaseViewModel(application) {

    abstract val nameInputError: LiveData<String>
    abstract val initialGroup: LiveData<Group>
    abstract val managers: LiveData<List<User>>
    abstract val remainingWorkers: LiveData<List<User>>
    abstract val selectedManager: LiveData<User>
    abstract val selectedWorkers: LiveData<List<User>>

    abstract fun onWorkerDeselected(worker: User)
    abstract fun onWorkerSelected(worker: User)
    abstract fun onManagerSelected(manager: User?)
    abstract fun updateGroup(group: Group, groupPatch: GroupPatch)


}