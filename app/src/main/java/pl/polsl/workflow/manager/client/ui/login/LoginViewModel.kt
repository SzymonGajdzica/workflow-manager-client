package pl.polsl.workflow.manager.client.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class LoginViewModel(application: Application): BaseViewModel(application) {

    abstract val user: LiveData<User>
    abstract val usernameInputError: LiveData<String>
    abstract val passwordInputError: LiveData<String>

    abstract fun login(username: String, password: String)
    abstract fun tryAutoLogin()

}