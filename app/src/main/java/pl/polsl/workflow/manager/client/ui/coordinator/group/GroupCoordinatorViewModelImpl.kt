package pl.polsl.workflow.manager.client.ui.coordinator.group

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.repository.GroupRepository
import javax.inject.Inject

class GroupCoordinatorViewModelImpl @Inject constructor(
    application: Application,
    private val groupRepository: GroupRepository
): GroupCoordinatorViewModel(application) {

    override val groups: MutableLiveData<List<Group>> = MutableLiveData()

    private fun loadGroups() = launchWithLoader {
        groups.value = null
        when(val result = groupRepository.getAllGroups()) {
            is RepositoryResult.Success -> groups.value = result.data
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    override fun reloadData() {
        super.reloadData()
        loadGroups()
    }

}
