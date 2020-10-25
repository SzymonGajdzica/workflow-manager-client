package pl.polsl.workflow.manager.client.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerHelper private constructor(
    private val viewModel: ViewModel,
    private val onLoop: () -> Unit
) {

    companion object {
        fun register(viewModel: ViewModel, onLoop: () -> Unit) {
            TimerHelper(viewModel, onLoop)
        }
    }

    init {
        start()
    }

    private fun start() {
        viewModel.viewModelScope.launch {
            onLoop()
            delay(200)
            start()
        }
    }

}