package pl.polsl.workflow.manager.client.ui.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.utils.TimerHelper
import pl.polsl.workflow.manager.client.utils.TokenHolder
import java.time.Instant

abstract class AccountViewModel(
    application: Application,
    private val tokenHolder: TokenHolder
): BaseViewModel(application) {

    private val mRemainingTime: MutableLiveData<Instant> = MutableLiveData(null)
    private var expirationDate: Instant? = tokenHolder.token?.expirationDate
    val remainingTime: LiveData<Instant> = mRemainingTime
    private var timerStarted = false

    fun logout() {
        tokenHolder.token = null
    }

    override fun reloadData() {
        super.reloadData()
        expirationDate = tokenHolder.token?.expirationDate
        if(!timerStarted)
            TimerHelper.register(this, ::updateRemainingTime)
        timerStarted = true
    }

    private fun updateRemainingTime() {
        mRemainingTime.value = expirationDate?.minusMillis(Instant.now().toEpochMilli())
    }

}