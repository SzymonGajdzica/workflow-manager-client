package pl.polsl.workflow.manager.client.ui.manager.account

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.GroupRepository
import pl.polsl.workflow.manager.client.utils.TokenHolder
import javax.inject.Inject

class AccountManagerViewModelImpl @Inject constructor(
    app: Application,
    private val groupRepository: GroupRepository,
    tokenHolder: TokenHolder
): AccountManagerViewModel(app, tokenHolder) {

    override val selectedGroup: MutableLiveData<Group> = MutableLiveData(null)
    override val groups: MutableLiveData<List<Group>> = MutableLiveData(null)

    private fun loadGroups() {
        launchWithLoader {
            when(val result = groupRepository.getAllGroups()) {
                is RepositoryResult.Success -> {
                    groups.value = result.data
                    selectedGroup.value = result.data.firstOrNull()
                }
                is RepositoryResult.Error -> showError(result.error)
            }
        }
    }

    override fun groupSelected(index: Int) {
        selectedGroup.value = groups.value?.get(index)
    }

    override fun reloadData() {
        super.reloadData()
        if(groups.value == null)
            loadGroups()
    }

}