package pl.polsl.workflow.manager.client.ui.manager.task.report.post

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskManagerReportPost
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class TaskManagerReportPostViewModel(application: Application): BaseViewModel(application) {

    abstract val descriptionInputError: LiveData<String>
    abstract val task: LiveData<Task>
    abstract val creatingFixTask: LiveData<Boolean>

    abstract fun acceptTask(taskManagerReportPost: TaskManagerReportPost, withFixTask: Boolean)

}