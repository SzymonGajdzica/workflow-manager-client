package pl.polsl.workflow.manager.client.ui.coordinator.account.post

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.UserPost
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class AccountCoordinatorPostViewModel(application: Application): BaseViewModel(application) {

    abstract val selectedRole: LiveData<Int>
    abstract val usernameInputError: LiveData<String>
    abstract val passwordInputError: LiveData<String>

    abstract fun onRoleSelected(role: Int)
    abstract fun createUser(userPost: UserPost)

}