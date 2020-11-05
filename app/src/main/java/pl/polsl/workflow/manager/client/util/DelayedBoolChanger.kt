package pl.polsl.workflow.manager.client.util

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DelayedBoolChanger(
        private val scope: CoroutineScope,
        private val delay: Long,
        private val liveData: MutableLiveData<Boolean>
) {

    private var job: Job? = null

    private var counter = 0

    val currentValue: Boolean
        get() = counter > 0

    fun change(value: Boolean) {
        val oldCounter = counter
        counter += if (value) 1 else -1
        if (oldCounter == 0 && counter == 1)
            scheduleJob(true)
        else if (oldCounter == 1 && counter == 0)
            scheduleJob(false)
    }

    private fun scheduleJob(value: Boolean) {
        if(job?.isActive == true)
            job?.cancel()
        job = scope.launch {
            delay(delay)
            liveData.value = value
        }
    }

}