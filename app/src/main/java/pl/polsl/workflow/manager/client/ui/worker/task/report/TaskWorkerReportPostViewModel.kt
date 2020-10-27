package pl.polsl.workflow.manager.client.ui.worker.task.report

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReportPost
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class TaskWorkerReportPostViewModel(application: Application): BaseViewModel(application) {
    
    abstract val descriptionInputError: LiveData<String>
    abstract fun sendReport(taskWorkerReportPost: TaskWorkerReportPost)

}