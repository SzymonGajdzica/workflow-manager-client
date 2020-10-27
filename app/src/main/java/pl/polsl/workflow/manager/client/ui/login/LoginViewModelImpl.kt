package pl.polsl.workflow.manager.client.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.hasLocationPermission
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.AuthenticationRepository
import pl.polsl.workflow.manager.client.model.remote.repository.UserRepository
import pl.polsl.workflow.manager.client.utils.TokenHolder
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor(
        private val app: Application,
        private val authenticationRepository: AuthenticationRepository,
        private val userRepository: UserRepository,
        private val tokenHolder: TokenHolder
): LoginViewModel(app) {

    override val user: MutableLiveData<User> = MutableLiveData()
    override val usernameInputError: MutableLiveData<String> = MutableLiveData()
    override val passwordInputError: MutableLiveData<String> = MutableLiveData()

    override fun login(username: String, password: String) = launchWithLoader {
        if (!validate(username, password))
            return@launchWithLoader
        when (val result = authenticationRepository.getAuthenticationToken(username, password)) {
            is RepositoryResult.Success -> {
                tokenHolder.token = result.data
                updateLoggedUserDetails()
            }
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    override fun clearErrorMessages() {
        super.clearErrorMessages()
        usernameInputError.value = null
        passwordInputError.value = null
    }

    private fun validate(username: String, password: String): Boolean {
        if(username.isBlank())
            usernameInputError.value = getString(R.string.cannotBeBlank)
        if(password.length < 3)
            passwordInputError.value = getString(R.string.passwordToShort)
        else if(password.isBlank())
            passwordInputError.value = getString(R.string.cannotBeBlank)
        return passwordInputError.value == null && usernameInputError.value == null
    }

    private fun tryAutoLogin() {
        val authenticationView = tokenHolder.token
        if(authenticationView != null)
            updateLoggedUserDetails()
    }

    private fun updateLoggedUserDetails() = launchWithLoader {
        if(!app.hasLocationPermission())
            return@launchWithLoader showToast(getString(R.string.locationPermissionRequired))
        when (val result = userRepository.getSelf()) {
            is RepositoryResult.Success -> user.value = result.data
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    override fun reloadData() {
        super.reloadData()
        tryAutoLogin()
    }

}