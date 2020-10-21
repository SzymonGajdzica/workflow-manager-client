package pl.polsl.workflow.manager.client.ui.worker.account

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.GroupView
import pl.polsl.workflow.manager.client.ui.BaseViewModel

abstract class AccountViewModel(application: Application) : BaseViewModel(application) {

    abstract val remainingTime: LiveData<Long>
    abstract val groupView: LiveData<GroupView>

    abstract fun loadAccountDetails()
    abstract fun logout(activity: Activity)

}