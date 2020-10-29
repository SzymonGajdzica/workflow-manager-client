package pl.polsl.workflow.manager.client.ui.manager.task.report.post

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.getParcelable
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskManagerReportPost
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepository
import javax.inject.Inject

class TaskManagerReportPostViewModelImpl @Inject constructor(
        application: Application,
        private val taskRepository: TaskRepository
): TaskManagerReportPostViewModel(application) {

    override val descriptionError: MutableLiveData<String> = MutableLiveData()
    override val task: MutableLiveData<Task> = MutableLiveData()
    override val creatingFixTask: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun acceptTask(taskManagerReportPost: TaskManagerReportPost, withFixTask: Boolean) = launchWithLoader {
        when(val result = taskRepository.sendManagerReport(taskManagerReportPost)) {
            is RepositoryResult.Success -> {
                showToast(getString(R.string.taskAccepted))
                if(withFixTask) {
                    creatingFixTask.value = true
                } else
                    finishFragment()
            }
            is RepositoryResult.Error -> showToast(result.error)
        }
    }

    override fun updateArguments(bundle: Bundle) {
        super.updateArguments(bundle)
        task.value = bundle.getParcelable()
    }


}