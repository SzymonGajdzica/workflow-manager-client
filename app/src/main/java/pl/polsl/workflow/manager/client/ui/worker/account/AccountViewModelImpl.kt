package pl.polsl.workflow.manager.client.ui.worker.account

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import pl.polsl.workflow.manager.client.model.data.GroupView
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.GroupRepository
import pl.polsl.workflow.manager.client.ui.login.LoginActivity
import pl.polsl.workflow.manager.client.utils.TimerHelper
import pl.polsl.workflow.manager.client.utils.TokenHolder
import java.util.*
import javax.inject.Inject
import kotlin.math.max

class AccountViewModelImpl @Inject constructor(
        application: Application,
        private val groupRepository: GroupRepository,
        private val tokenHolder: TokenHolder
): AccountViewModel(application) {

    override val remainingTime: MutableLiveData<Long> = MutableLiveData(0L)
    override val groupView: MutableLiveData<GroupView> = MutableLiveData()
    private val timerHelper = TimerHelper(viewModelScope, ::updateRemainingTime).apply { running = true }

    override fun loadAccountDetails() {
        if(groupView.value != null || errorMessage.value != null)
            return
        launchWithLoader {
            when(val result = groupRepository.getWorkerGroup()) {
                is RepositoryResult.Success -> groupView.value = result.data
                is RepositoryResult.Error -> showError(result.error)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerHelper.running = false
    }

    override fun logout(activity: Activity) {
        tokenHolder.token = null
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }

    override fun reloadData() {
        super.reloadData()
        loadAccountDetails()
    }

    private fun updateRemainingTime() {
        remainingTime.value = max((tokenHolder.token?.expirationDate?.time ?: 0L) - Date().time, 0L)
    }


}