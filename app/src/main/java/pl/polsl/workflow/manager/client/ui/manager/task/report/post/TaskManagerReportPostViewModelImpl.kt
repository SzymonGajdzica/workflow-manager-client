package pl.polsl.workflow.manager.client.ui.manager.task.report.post

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskManagerReportPost
import pl.polsl.workflow.manager.client.model.repository.TaskRepository
import pl.polsl.workflow.manager.client.util.extension.getParcelable
import pl.polsl.workflow.manager.client.util.validator.InputValidator
import javax.inject.Inject

class TaskManagerReportPostViewModelImpl @Inject constructor(
        application: Application,
        private val taskRepository: TaskRepository,
        private val inputValidator: InputValidator
): TaskManagerReportPostViewModel(application) {

    override val descriptionInputError: MutableLiveData<String> = MutableLiveData()
    override val task: MutableLiveData<Task> = MutableLiveData()
    override val creatingFixTask: MutableLiveData<Boolean> = MutableLiveData(false)

    private var createdFixTask = false

    override fun acceptTask(taskManagerReportPost: TaskManagerReportPost, withFixTask: Boolean) = launchWithLoader {
        descriptionInputError.value = inputValidator.validateBlankText(taskManagerReportPost.description)
        if (descriptionInputError.value != null) {
            when (val result = taskRepository.sendManagerReport(taskManagerReportPost)) {
                is RepositoryResult.Success -> {
                    if (withFixTask) {
                        creatingFixTask.value = true
                        createdFixTask = true
                        creatingFixTask.value = false
                    } else {
                        showSuccessMessage(getString(R.string.taskAccepted))
                        finishFragment()
                    }
                }
                is RepositoryResult.Error -> showErrorMessage(result.error)
            }
        }
    }

    override fun updateArguments(bundle: Bundle) {
        super.updateArguments(bundle)
        task.value = bundle.getParcelable()
    }

    override fun reloadData() {
        super.reloadData()
        if(createdFixTask)
            finishFragment()
    }


}