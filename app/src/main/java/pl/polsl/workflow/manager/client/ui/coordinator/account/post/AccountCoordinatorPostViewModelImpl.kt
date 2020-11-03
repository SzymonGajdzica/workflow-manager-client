package pl.polsl.workflow.manager.client.ui.coordinator.account.post

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.UserPost
import pl.polsl.workflow.manager.client.model.repository.UserRepository
import pl.polsl.workflow.manager.client.util.validator.InputValidator
import javax.inject.Inject

class AccountCoordinatorPostViewModelImpl @Inject constructor(
        application: Application,
        private val userRepository: UserRepository,
        private val inputValidator: InputValidator
): AccountCoordinatorPostViewModel(application) {

    override val usernameInputError: MutableLiveData<String> = MutableLiveData()
    override val passwordInputError: MutableLiveData<String> = MutableLiveData()

    override fun createUser(userPost: UserPost) = launchWithLoader {
        if(validate(userPost)){
            when(val result = userRepository.createUser(userPost)) {
                is RepositoryResult.Success -> {
                    showSuccessMessage(getString(R.string.userCreated))
                    finishFragment()
                }
                is RepositoryResult.Error -> showErrorMessage(result.error)
            }
        }
    }

    private fun validate(userPost: UserPost): Boolean {
        usernameInputError.value = inputValidator.validateUsername(userPost.username)
        passwordInputError.value = inputValidator.validatePassword(userPost.password)
        return usernameInputError.value == null && passwordInputError.value == null
    }

}