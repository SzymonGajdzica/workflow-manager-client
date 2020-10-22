package pl.polsl.workflow.manager.client.ui

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import retrofit2.HttpException

abstract class BaseViewModel(private val app: Application): AndroidViewModel(app) {

    private val mLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val mErrorString: MutableLiveData<String> = MutableLiveData<String>(null)
    private val mErrorMessage: MutableLiveData<String> = MutableLiveData<String>(null)

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

    protected fun showToast(text: String) {
        mErrorMessage.value = text
    }

    protected fun showError(errorText: String) {
        App.log("Error", errorText)
        mErrorString.value = errorText
    }

    protected fun showError(e: Throwable) {
        val message = runCatching {
            (e as HttpException).response()?.errorBody()?.charStream()?.readText()?.let {
                App.log(it)
                JSONObject(it).getString("message")
            }
        }.getOrNull()
        e.printStackTrace()
        showError(message ?: getString(R.string.unknownError))
    }

}