package pl.polsl.workflow.manager.client.ui.manager.task.post

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskPost
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel
import java.time.Instant

abstract class TaskManagerPostViewModel(application: Application): BaseViewModel(application) {

    abstract val subTask: LiveData<Task>
    abstract val group: LiveData<Group>
    abstract val selectedWorker: LiveData<User>
    abstract val descriptionInputError: LiveData<String>
    abstract val nameInputError: LiveData<String>
    abstract val deadline: LiveData<Instant>
    abstract val executionTime: LiveData<Instant>

    abstract fun updateSelectedWorker(worker: User?)
    abstract fun updateExecutionTime(executionTime: Instant)
    abstract fun updateDeadline(deadline: Instant)
    abstract fun createTask(taskPost: TaskPost)

}