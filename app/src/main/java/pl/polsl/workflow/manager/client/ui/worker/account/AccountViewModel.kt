package pl.polsl.workflow.manager.client.ui.worker.account

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel
import java.time.Instant

abstract class AccountViewModel(application: Application) : BaseViewModel(application) {

    abstract val remainingTime: LiveData<Instant>
    abstract val group: LiveData<Group>

    abstract fun loadAccountDetails()
    abstract fun logout()

}