package pl.polsl.workflow.manager.client.ui.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.remote.repositoryMessage

abstract class BaseViewModel(private val app: Application): AndroidViewModel(app) {

    private val mShouldFinish: MutableLiveData<Boolean> = MutableLiveData(false)
    private val mLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val mErrorString: MutableLiveData<String> = MutableLiveData<String>(null)
    private val mErrorMessage: MutableLiveData<String> = MutableLiveData<String>(null)

    val shouldFinish: LiveData<Boolean> = mShouldFinish
    val loading: LiveData<Boolean> = mLoading
    val error: LiveData<String> = mErrorString
    val errorMessage: LiveData<String> = mErrorMessage

    open fun clearErrorMessages() {
        mErrorMessage.value = null
    }

    open fun reloadData() {
        mErrorString.value = null
    }

    protected fun getString(@StringRes stringRes: Int): String {
        return app.getString(stringRes)
    }

    protected fun startLoading() {
        mLoading.value = true
    }

    protected fun stopLoading() {
        mLoading.value = false
    }

    protected fun launchWithLoader(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            startLoading()
            block()
            stopLoading()
        }
    }

    protected fun finishFragment() {
        mShouldFinish.value = true
    }

    protected fun showToast(text: String) {
        mErrorMessage.value = text
    }

    protected fun showToast(e: Throwable) {
        showToast(e.repositoryMessage ?: getString(R.string.unknownError))
    }

    protected fun showError(errorText: String) {
        App.log("Error", errorText)
        mErrorString.value = errorText
    }

    protected fun showError(e: Throwable) {
        e.printStackTrace()
        showError(e.repositoryMessage ?: getString(R.string.unknownError))
    }

}