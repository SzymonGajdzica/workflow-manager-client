package pl.polsl.workflow.manager.client.ui.worker.account

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.ui.base.AccountViewModel
import pl.polsl.workflow.manager.client.utils.TokenHolder

abstract class AccountWorkerViewModel(application: Application, tokenHolder: TokenHolder) : AccountViewModel(application, tokenHolder) {

    abstract val group: LiveData<Group>

}