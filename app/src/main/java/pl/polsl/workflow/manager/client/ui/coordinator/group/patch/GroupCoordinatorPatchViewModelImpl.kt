package pl.polsl.workflow.manager.client.ui.coordinator.group.patch

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.GroupPatch
import pl.polsl.workflow.manager.client.model.data.Role
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.repository.GroupRepository
import pl.polsl.workflow.manager.client.model.repository.UserRepository
import pl.polsl.workflow.manager.client.util.extension.get
import pl.polsl.workflow.manager.client.util.extension.safeValue
import pl.polsl.workflow.manager.client.util.validator.InputValidator
import javax.inject.Inject

class GroupCoordinatorPatchViewModelImpl @Inject constructor(
        application: Application,
        private val groupRepository: GroupRepository,
        private val userRepository: UserRepository,
        private val inputValidator: InputValidator
): GroupCoordinatorPatchViewModel(application) {

    private var allWorkers: ArrayList<User>? = null

    override val nameInputError: MutableLiveData<String> = MutableLiveData()
    override val initialGroup: MutableLiveData<Group> = MutableLiveData()
    override val managers: MutableLiveData<List<User>> = MutableLiveData()
    override val remainingWorkers: MutableLiveData<List<User>> = MutableLiveData()
    override val selectedManager: MutableLiveData<User> = MutableLiveData()
    override val selectedWorkers: MutableLiveData<List<User>> = MutableLiveData()

    override fun onWorkerDeselected(worker: User) {
        selectedWorkers.value = selectedWorkers.safeValue.filter { it != worker }
        updateRemainingWorkers()
    }

    override fun onWorkerSelected(worker: User) {
        val newList = arrayListOf(worker)
        newList.addAll(selectedWorkers.safeValue)
        selectedWorkers.value = newList.sortedBy { it.username }
        updateRemainingWorkers()
    }

    override fun onManagerSelected(manager: User?) {
        selectedManager.value = manager
    }

    private fun loadUsers() = launchWithLoader {
        managers.value = null
        remainingWorkers.value = null
        allWorkers = null
        when (val result = userRepository.getAllUsers()) {
            is RepositoryResult.Success -> {
                managers.value = result.data.filter { it.role == Role.MANAGER }
                allWorkers = ArrayList(result.data.filter { it.role == Role.WORKER })
                updateRemainingWorkers()
            }
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    private fun updateRemainingWorkers() {
        remainingWorkers.value = allWorkers
                ?.filter { !selectedWorkers.safeValue.contains(it) }
    }

    override fun updateGroup(group: Group, groupPatch: GroupPatch) = launchWithLoader {
        nameInputError.value = inputValidator.validateBlankText(groupPatch.name)
        if(nameInputError.value == null) {
            when(val result = groupRepository.updateGroup(group, groupPatch)) {
                is RepositoryResult.Success -> {
                    showSuccessMessage(getString(R.string.groupUpdated))
                    finishFragment()
                }
                is RepositoryResult.Error -> showErrorMessage(result.error)
            }
        }
    }

    override fun reloadData() {
        super.reloadData()
        loadUsers()
    }

    override fun updateArguments(bundle: Bundle) {
        super.updateArguments(bundle)
        val group: Group? = bundle.get()
        if(initialGroup.value == null && group != null) {
            initialGroup.value = group
            selectedManager.value = group.manager
            selectedWorkers.value = group.workers
        }
    }

}