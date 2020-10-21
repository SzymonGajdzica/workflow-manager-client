package pl.polsl.workflow.manager.client.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerHelper(
    private val scope: CoroutineScope,
    private val onLoop: () -> Unit) {

    private var currentJob: Job? = null

    var running: Boolean
        get() = currentJob?.isActive == true
        set(value) {
            stop()
            if (value)
                start()
        }

    private fun stop() {
        currentJob?.cancel()
        currentJob = null
    }

    private fun start() {
        currentJob = scope.launch {
            onLoop()
            delay(200)
            start()
        }
    }

}