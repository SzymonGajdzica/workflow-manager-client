package pl.polsl.workflow.manager.client.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.data.checkedToken
import pl.polsl.workflow.manager.client.model.repository.AuthenticationRepository
import pl.polsl.workflow.manager.client.model.repository.UserRepository
import pl.polsl.workflow.manager.client.util.extension.hasLocationPermission
import pl.polsl.workflow.manager.client.util.token.TokenHolder
import pl.polsl.workflow.manager.client.util.validator.InputValidator
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor(
        private val app: Application,
        private val authenticationRepository: AuthenticationRepository,
        private val userRepository: UserRepository,
        private val tokenHolder: TokenHolder,
        private val inputValidator: InputValidator
): LoginViewModel(app) {

    override val user: MutableLiveData<User> = MutableLiveData()
    override val usernameInputError: MutableLiveData<String> = MutableLiveData()
    override val passwordInputError: MutableLiveData<String> = MutableLiveData()

    override fun login(username: String, password: String) = launchWithLoader {
        usernameInputError.value = inputValidator.validateUsername(username)
        passwordInputError.value = inputValidator.validatePassword(password)
        if (passwordInputError.value == null && usernameInputError.value == null) {
            when (val result = authenticationRepository.getAuthenticationToken(username, password)) {
                is RepositoryResult.Success -> {
                    tokenHolder.token = result.data
                    updateLoggedUserDetails()
                }
                is RepositoryResult.Error -> showErrorMessage(result.error)
            }
        }
    }

    private fun tryAutoLogin() {
        val authenticationView = tokenHolder.token?.checkedToken
        if(authenticationView != null)
            updateLoggedUserDetails()
    }

    private fun updateLoggedUserDetails() = launchWithLoader {
        if(!app.hasLocationPermission())
            return@launchWithLoader showErrorMessage(getString(R.string.locationPermissionRequired))
        when (val result = userRepository.getSelf()) {
            is RepositoryResult.Success -> user.value = result.data
            is RepositoryResult.Error -> showErrorMessage(result.error)
        }
    }

    override fun reloadData() {
        super.reloadData()
        tryAutoLogin()
    }

}