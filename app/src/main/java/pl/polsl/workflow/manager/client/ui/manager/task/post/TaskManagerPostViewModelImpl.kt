package pl.polsl.workflow.manager.client.ui.manager.task.post

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.getParcelable
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskPost
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepository
import java.time.Instant
import javax.inject.Inject

class TaskManagerPostViewModelImpl @Inject constructor(
    app: Application,
    private val taskRepository: TaskRepository
): TaskManagerPostViewModel(app) {

    override val subTask: MutableLiveData<Task> = MutableLiveData()
    override val group: MutableLiveData<Group> = MutableLiveData()
    override val selectedWorkerIndex: MutableLiveData<Int> = MutableLiveData(null)
    override val descriptionInputError: MutableLiveData<String> = MutableLiveData()
    override val nameInputError: MutableLiveData<String> = MutableLiveData()
    override val executionTime: MutableLiveData<Instant> = MutableLiveData(Instant.ofEpochMilli(1000L * 60L * 30L))
    override val deadline: MutableLiveData<Instant> = MutableLiveData(Instant.now())

    override fun updateSelectedWorkerIndex(index: Int?) {
        selectedWorkerIndex.value = index
    }

    override fun updateExecutionTime(executionTime: Instant) {
        this.executionTime.value = executionTime
    }

    override fun updateDeadline(deadline: Instant) {
        this.deadline.value = deadline
    }

    override fun createTask(taskPost: TaskPost) = launchWithLoader {
        if (!validate(taskPost))
            return@launchWithLoader
        when (val result = taskRepository.addTask(taskPost)) {
            is RepositoryResult.Success -> {
                showSuccessMessage(getString(R.string.taskCreated))
                finishFragment()
            }
            is RepositoryResult.Error -> showErrorMessage(result.error)
        }
    }

    private fun validate(taskPost: TaskPost): Boolean {
        nameInputError.value = if(taskPost.name.isBlank())
            getString(R.string.cannotBeBlank)
        else null
        descriptionInputError.value = if(taskPost.description.isBlank())
            getString(R.string.cannotBeBlank)
        else null
        return descriptionInputError.value == null && nameInputError.value == null
    }

    override fun updateArguments(bundle: Bundle) {
        super.updateArguments(bundle)
        group.value = bundle.getParcelable()
        subTask.value = bundle.getParcelable()
    }

}