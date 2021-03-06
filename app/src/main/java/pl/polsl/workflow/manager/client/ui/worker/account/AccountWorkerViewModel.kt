package pl.polsl.workflow.manager.client.ui.worker.account

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.ui.account.AccountViewModel
import pl.polsl.workflow.manager.client.util.token.TokenHolder

abstract class AccountWorkerViewModel(application: Application, tokenHolder: TokenHolder) : AccountViewModel(application, tokenHolder) {

    abstract val group: LiveData<Group>

}