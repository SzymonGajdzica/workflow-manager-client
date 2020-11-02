package pl.polsl.workflow.manager.client.ui.coordinator.account

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Role
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.data.UserPatch
import pl.polsl.workflow.manager.client.model.repository.UserRepository
import pl.polsl.workflow.manager.client.util.token.TokenHolder
import javax.inject.Inject

class AccountCoordinatorViewModelImpl @Inject constructor(
    application: Application,
    tokenHolder: TokenHolder,
    private val userRepository: UserRepository
): AccountCoordinatorViewModel(application, tokenHolder) {

    override val users: MutableLiveData<List<User>> = MutableLiveData()
    private var allUsers: List<User>? = null
    private var selectedRole: Int = Role.WORKER

    private fun loadUsers() = launchWithLoader {
        users.value = null
        allUsers = null
        when(val result = userRepository.getAllUsers()) {
            is RepositoryResult.Success -> {
                allUsers = result.data
                showFilteredList()

            }
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    override fun updateUser(user: User, userPatch: UserPatch) = launchWithLoader {
        when(val result = userRepository.updateUser(user, userPatch)) {
            is RepositoryResult.Success -> {
                if(result.data.enabled)
                    showSuccessMessage(getString(R.string.userEnabled))
                else
                    showSuccessMessage(getString(R.string.userDisabled))
                loadUsers()
            }
            is RepositoryResult.Error -> showErrorMessage(result.error)
        }
    }

    private fun showFilteredList() {
        users.value = allUsers?.filter { it.role == selectedRole }
    }

    override fun roleSelected(role: Int) {
        this.selectedRole = role
        showFilteredList()
    }

    override fun reloadData() {
        super.reloadData()
        loadUsers()
    }

}