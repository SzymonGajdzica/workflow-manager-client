package pl.polsl.workflow.manager.client.ui.worker.account

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.GroupRepository
import pl.polsl.workflow.manager.client.utils.TimerHelper
import pl.polsl.workflow.manager.client.utils.TokenHolder
import java.time.Instant
import javax.inject.Inject

class AccountWorkerViewModelImpl @Inject constructor(
        application: Application,
        private val groupRepository: GroupRepository,
        private val tokenHolder: TokenHolder
): AccountWorkerViewModel(application) {

    override val remainingTime: MutableLiveData<Instant> = MutableLiveData(Instant.ofEpochMilli(0L))
    override val group: MutableLiveData<Group> = MutableLiveData()
    private val expirationDate: Instant? = tokenHolder.token?.expirationDate

    init {
        TimerHelper.register(this, ::updateRemainingTime)
    }

    private fun loadAccountDetails() {
        launchWithLoader {
            when(val result = groupRepository.getWorkerGroup()) {
                is RepositoryResult.Success -> group.value = result.data
                is RepositoryResult.Error -> showError(result.error)
            }
        }
    }

    override fun logout() {
        tokenHolder.token = null
    }

    override fun reloadData() {
        super.reloadData()
        if(group.value == null)
            loadAccountDetails()
    }

    private fun updateRemainingTime() {
        remainingTime.value = expirationDate?.minusMillis(Instant.now().toEpochMilli())
    }


}