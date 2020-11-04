package pl.polsl.workflow.manager.client.ui.worker.task

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel
import java.time.Instant

abstract class TaskWorkerViewModel(application: Application): BaseViewModel(application) {

    abstract val tasks: LiveData<List<Task>>
    abstract val task: LiveData<Task>
    abstract val remainingTime: LiveData<Instant>
    abstract val selectedTaskStatus: LiveData<Int>

    abstract fun startTask()
    abstract fun taskStatusSelected(taskStatus: Int)
    abstract fun getSharedTasks(task: Task): List<Task>

}