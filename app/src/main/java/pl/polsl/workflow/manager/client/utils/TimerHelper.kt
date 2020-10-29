package pl.polsl.workflow.manager.client.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerHelper private constructor(
        private val scope: CoroutineScope,
        private val onLoop: () -> Unit
) {

    companion object {
        fun register(viewModel: ViewModel, onLoop: () -> Unit) {
            register(viewModel.viewModelScope, onLoop)
        }
        fun register(scope: CoroutineScope, onLoop: () -> Unit) {
            TimerHelper(scope, onLoop)
        }
    }

    init {
        loop()
    }

    private fun loop() {
        scope.launch {
            onLoop()
            delay(200)
            loop()
        }
    }

}