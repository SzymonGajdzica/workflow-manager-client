package pl.polsl.workflow.manager.client.ui.coordinator.group.post

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.GroupPost
import pl.polsl.workflow.manager.client.model.data.Role
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.repository.GroupRepository
import pl.polsl.workflow.manager.client.model.repository.UserRepository
import pl.polsl.workflow.manager.client.util.validator.InputValidator
import javax.inject.Inject

class GroupCoordinatorPostViewModelImpl @Inject constructor(
        application: Application,
        private val userRepository: UserRepository,
        private val groupRepository: GroupRepository,
        private val inputValidator: InputValidator
): GroupCoordinatorPostViewModel(application) {

    override val nameInputError: MutableLiveData<String> = MutableLiveData()
    override val managers: MutableLiveData<List<User>> = MutableLiveData()

    private fun loadManagers() = launchWithLoader {
        managers.value = null
        when(val result = userRepository.getAllUsers()) {
            is RepositoryResult.Success -> managers.value = result.data.filter { it.role == Role.MANAGER }
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    override fun createGroup(groupPost: GroupPost) = launchWithLoader {
        nameInputError.value = inputValidator.validateBlankText(groupPost.name)
        if(nameInputError.value == null) {
            when(val result = groupRepository.createGroup(groupPost)) {
                is RepositoryResult.Success -> {
                    showSuccessMessage(getString(R.string.groupCreated))
                    finishFragment()
                }
                is RepositoryResult.Error -> showErrorMessage(result.error)
            }
        }
    }

    override fun reloadData() {
        super.reloadData()
        loadManagers()
    }

}