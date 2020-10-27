package pl.polsl.workflow.manager.client.ui.worker.task

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel
import java.time.Instant

abstract class TaskWorkerViewModel(application: Application): BaseViewModel(application) {

    abstract val task: LiveData<Task>
    abstract val remainingTime: LiveData<Instant>

    abstract fun startTask()

}