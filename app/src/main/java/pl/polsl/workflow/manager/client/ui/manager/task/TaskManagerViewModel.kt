package pl.polsl.workflow.manager.client.ui.manager.task

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class TaskManagerViewModel(application: Application): BaseViewModel(application) {

    abstract val selectedGroup: LiveData<Group>
    abstract val groups: LiveData<List<Group>>
    abstract val tasks: LiveData<List<Task>>

    abstract fun groupSelected(group: Group)
    abstract fun taskStatusSelected(taskStatus: Int)
    abstract fun removeTask(task: Task)
    abstract fun getSharedTasks(task: Task): List<Task>

}