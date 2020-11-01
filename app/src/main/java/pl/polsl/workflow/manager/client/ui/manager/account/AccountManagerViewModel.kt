package pl.polsl.workflow.manager.client.ui.manager.account

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.ui.account.AccountViewModel
import pl.polsl.workflow.manager.client.util.token.TokenHolder

abstract class AccountManagerViewModel(application: Application, tokenHolder: TokenHolder): AccountViewModel(application, tokenHolder) {

    abstract val selectedGroup: LiveData<Group>
    abstract val groups: LiveData<List<Group>>

    abstract fun groupSelected(index: Int)

}