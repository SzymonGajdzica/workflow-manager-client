package pl.polsl.workflow.manager.client.ui.base

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.repositoryMessage
import pl.polsl.workflow.manager.client.util.DelayedBoolChanger
import java.util.*

abstract class BaseViewModel(private val app: Application): AndroidViewModel(app) {


    private val mShouldFinish: MutableLiveData<Boolean> = MutableLiveData(false)
    private val mLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val mErrorString: MutableLiveData<String> = MutableLiveData<String>(null)
    private val mErrorMessage: MutableLiveData<String> = MutableLiveData<String>(null)
    private val mSuccessMessage: MutableLiveData<String> = MutableLiveData<String>(null)

    val shouldFinish: LiveData<Boolean> = mShouldFinish
    val loading: LiveData<Boolean> = mLoading
    val error: LiveData<String> = mErrorString
    val errorMessage: LiveData<String> = mErrorMessage
    val successMessage: LiveData<String> = mSuccessMessage

    private val delayedValueChanger = DelayedBoolChanger(viewModelScope, 200L, mLoading)

    fun clearErrorString() {
        mErrorString.value = null
    }

    fun clearMessages() {
        mErrorMessage.value = null
        mSuccessMessage.value = null
    }

    open fun updateSharedArguments(intent: Intent) {

    }

    open fun updateArguments(bundle: Bundle) {

    }

    open fun reloadData() {

    }

    protected fun getString(@StringRes stringRes: Int): String {
        return app.getString(stringRes)
    }

    protected fun launchWithLoader(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            delayedValueChanger.change(true)
            block()
            delayedValueChanger.change(false)
        }
    }

    protected fun finishFragment() {
        mShouldFinish.value = true
    }

    protected fun showSuccessMessage(text: String) {
        mSuccessMessage.value = text
    }

    protected fun showErrorMessage(text: String) {
        mErrorMessage.value = text
    }

    protected fun showErrorMessage(e: Throwable) {
        showErrorMessage((e.repositoryMessage ?: e.message ?: getString(R.string.unknownError)).capitalize(Locale.US))
    }

    protected fun showError(errorText: String) {
        mErrorString.value = errorText
    }

    protected fun showError(e: Throwable) {
        e.printStackTrace()
        showError((e.repositoryMessage ?: e.message ?: getString(R.string.unknownError)).capitalize(Locale.US))
    }



}