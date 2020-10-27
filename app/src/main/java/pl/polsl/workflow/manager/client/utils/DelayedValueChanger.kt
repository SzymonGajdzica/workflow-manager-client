package pl.polsl.workflow.manager.client.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DelayedValueChanger<T: Any>(
        private val scope: CoroutineScope,
        private val delay: Long,
        private val liveData: MutableLiveData<T>
) {

    private var job: Job? = null

    fun change(value: T) {
        job?.cancel()
        job = null
        if(liveData.value == value)
            return
        job = scope.launch {
            delay(delay)
            liveData.value = value
        }
    }

}