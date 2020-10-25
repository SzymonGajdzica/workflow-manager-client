package pl.polsl.workflow.manager.client.ui.worker.task

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepository
import pl.polsl.workflow.manager.client.utils.TimerHelper
import java.time.Instant
import javax.inject.Inject

class TaskViewModelImpl @Inject constructor(
    application: Application,
    private val taskRepository: TaskRepository
): TaskViewModel(application) {

    override val task: MutableLiveData<Task> = MutableLiveData()
    override val remainingTime: MutableLiveData<Instant> = MutableLiveData()

    init {
        TimerHelper.register(this, ::updateRemainingTime)
    }

    override fun loadTask() {
        launchWithLoader {
            task.value = null
            when(val result = taskRepository.getCurrentTask()) {
                is RepositoryResult.Success -> task.value = result.data
                is RepositoryResult.Error -> getNextTask(false)
            }
        }
    }

    override fun startTask() {
        launchWithLoader {
            getNextTask(true)
        }
    }

    private suspend fun getNextTask(autoStart: Boolean) {
        when(val result = taskRepository.getNextTask(autoStart)) {
            is RepositoryResult.Success -> task.value = result.data
            is RepositoryResult.Error -> showToast(result.error)
        }
    }

    override fun reloadData() {
        super.reloadData()
        loadTask()
    }

    private fun updateRemainingTime() {
        remainingTime.value = task.value?.let { task ->
            val startDate = task.startDate
            if(startDate != null)
                task.startDate.plusMillis(task.estimatedExecutionTime.toEpochMilli())?.minusMillis(Instant.now().toEpochMilli())
            else
                task.estimatedExecutionTime
        }
    }

}