package pl.polsl.workflow.manager.client.ui.worker.task

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskStatus
import pl.polsl.workflow.manager.client.model.data.status
import pl.polsl.workflow.manager.client.model.repository.TaskRepository
import pl.polsl.workflow.manager.client.util.TimerHelper
import java.time.Instant
import javax.inject.Inject

class TaskWorkerViewModelImpl @Inject constructor(
    application: Application,
    private val taskRepository: TaskRepository
): TaskWorkerViewModel(application) {

    private var allTasks: List<Task>? = null
    override val tasks: MutableLiveData<List<Task>> = MutableLiveData()
    override val task: MutableLiveData<Task> = MutableLiveData()
    override val remainingTime: MutableLiveData<Instant> = MutableLiveData()
    override val selectedTaskStatus: MutableLiveData<Int> = MutableLiveData(TaskStatus.FINISHED)

    init {
        TimerHelper.register(this, ::updateRemainingTime)
    }

    private fun loadTask() = launchWithLoader {
        task.value = null
        when (val result = taskRepository.getCurrentTask()) {
            is RepositoryResult.Success -> task.value = result.data
            is RepositoryResult.Error -> getNextTask(false)
        }
    }

    override fun startTask() = launchWithLoader {
        getNextTask(true)
    }

    override fun getSharedTasks(task: Task): List<Task> {
        return allTasks?.filter { it.sharedTaskId == task.sharedTaskId } ?: listOf()
    }

    override fun taskStatusSelected(taskStatus: Int) {
        selectedTaskStatus.value = taskStatus
        setFilteredTasks()
    }

    private suspend fun getNextTask(autoStart: Boolean) {
        when(val result = taskRepository.getNextTask(autoStart)) {
            is RepositoryResult.Success -> task.value = result.data
            is RepositoryResult.Error -> showErrorMessage(result.error)
        }
    }

    private fun loadTasks() = launchWithLoader {
        allTasks = null
        tasks.value = null
        when(val result = taskRepository.getWorkerTasks()) {
            is RepositoryResult.Success -> {
                allTasks = result.data
                setFilteredTasks()
            }
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    private fun setFilteredTasks() {
        val allTasks = allTasks ?: return
        tasks.value = allTasks.filter { it.status == selectedTaskStatus.value }.sortedBy { it.deadline }
    }

    override fun reloadData() {
        super.reloadData()
        loadTask()
        loadTasks()
    }

    private fun updateRemainingTime() {
        remainingTime.value = task.value?.let { task ->
            val startDate = task.startDate
            if(startDate != null)
                startDate.plusMillis(task.estimatedExecutionTime.toEpochMilli()).minusMillis(Instant.now().toEpochMilli())
            else
                task.estimatedExecutionTime
        }
    }

}