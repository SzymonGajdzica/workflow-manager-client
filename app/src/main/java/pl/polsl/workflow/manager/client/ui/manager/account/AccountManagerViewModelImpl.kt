package pl.polsl.workflow.manager.client.ui.manager.account

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.repository.GroupRepository
import pl.polsl.workflow.manager.client.util.token.TokenHolder
import javax.inject.Inject

class AccountManagerViewModelImpl @Inject constructor(
    app: Application,
    private val groupRepository: GroupRepository,
    tokenHolder: TokenHolder
): AccountManagerViewModel(app, tokenHolder) {

    override val selectedGroup: MutableLiveData<Group> = MutableLiveData(null)
    override val groups: MutableLiveData<List<Group>> = MutableLiveData(null)

    private fun loadGroups() = launchWithLoader {
        groups.value = null
        when (val result = groupRepository.getAllGroups()) {
            is RepositoryResult.Success -> {
                groups.value = result.data
                if(selectedGroup.value == null)
                    selectedGroup.value = result.data.firstOrNull()
            }
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    override fun groupSelected(group: Group) {
        selectedGroup.value = group
    }

    override fun reloadData() {
        super.reloadData()
        loadGroups()
    }

}