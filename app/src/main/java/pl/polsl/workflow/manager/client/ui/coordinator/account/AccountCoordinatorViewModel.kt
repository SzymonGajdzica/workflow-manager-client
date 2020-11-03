package pl.polsl.workflow.manager.client.ui.coordinator.account

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.model.data.UserPatch
import pl.polsl.workflow.manager.client.ui.account.AccountViewModel
import pl.polsl.workflow.manager.client.util.token.TokenHolder

abstract class AccountCoordinatorViewModel(application: Application, tokenHolder: TokenHolder): AccountViewModel(application, tokenHolder) {

    abstract val users: LiveData<List<User>>

    abstract fun roleSelected(role: Int)
    abstract fun updateUser(user: User, userPatch: UserPatch)

}