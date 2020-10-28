package pl.polsl.workflow.manager.client.ui.worker.account

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.GroupRepository
import pl.polsl.workflow.manager.client.utils.TokenHolder
import javax.inject.Inject

class AccountWorkerViewModelImpl @Inject constructor(
        application: Application,
        private val groupRepository: GroupRepository,
        tokenHolder: TokenHolder
): AccountWorkerViewModel(application, tokenHolder) {

    override val group: MutableLiveData<Group> = MutableLiveData()

    private fun loadAccountDetails() = launchWithLoader {
        when (val result = groupRepository.getWorkerGroup()) {
            is RepositoryResult.Success -> group.value = result.data
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    override fun reloadData() {
        super.reloadData()
        if(group.value == null)
            loadAccountDetails()
    }

}