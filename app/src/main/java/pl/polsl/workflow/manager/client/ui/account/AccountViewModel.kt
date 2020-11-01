package pl.polsl.workflow.manager.client.ui.account

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel
import pl.polsl.workflow.manager.client.util.TimerHelper
import pl.polsl.workflow.manager.client.util.token.TokenHolder
import java.time.Instant

abstract class AccountViewModel(
    application: Application,
    private val tokenHolder: TokenHolder
): BaseViewModel(application) {

    private val mRemainingTime: MutableLiveData<Instant> = MutableLiveData(null)
    private var expirationDate: Instant? = tokenHolder.token?.expirationDate
    private var mUser: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = mUser
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

    override fun updateSharedArguments(intent: Intent) {
        super.updateSharedArguments(intent)
        mUser.value = intent.getParcelableExtra("user")
    }

}