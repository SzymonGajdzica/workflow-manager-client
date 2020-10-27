package pl.polsl.workflow.manager.client.ui.manager.task.post

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.TaskPost
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepository
import java.time.Instant
import javax.inject.Inject

class TaskManagerPostViewModelImpl @Inject constructor(
    app: Application,
    private val taskRepository: TaskRepository
): TaskManagerPostViewModel(app) {

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

    override fun createTask(taskPost: TaskPost) {
        launchWithLoader {
            if(validate(taskPost))
                when(val result = taskRepository.addTask(taskPost)) {
                    is RepositoryResult.Success -> {
                        showToast(getString(R.string.taskCreated))
                        finishFragment()
                    }
                    is RepositoryResult.Error -> showToast(result.error)
                }
        }
    }

    private fun validate(taskPost: TaskPost): Boolean {
        if(taskPost.name.isBlank())
            nameInputError.value = getString(R.string.cannotBeBlank)
        if(taskPost.description.isBlank())
            descriptionInputError.value = getString(R.string.cannotBeBlank)
        return descriptionInputError.value == null && nameInputError.value == null
    }

    override fun clearErrorMessages() {
        super.clearErrorMessages()
        descriptionInputError.value = null
        nameInputError.value = null
    }

}