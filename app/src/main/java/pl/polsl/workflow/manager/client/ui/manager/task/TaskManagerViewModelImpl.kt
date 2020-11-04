package pl.polsl.workflow.manager.client.ui.manager.task

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskStatus
import pl.polsl.workflow.manager.client.model.data.status
import pl.polsl.workflow.manager.client.model.repository.GroupRepository
import pl.polsl.workflow.manager.client.model.repository.TaskRepository
import javax.inject.Inject

class TaskManagerViewModelImpl @Inject constructor(
    app: Application,
    private val taskRepository: TaskRepository,
    private val groupRepository: GroupRepository
): TaskManagerViewModel(app) {

    override val groups: MutableLiveData<List<Group>> = MutableLiveData()
    override val tasks: MutableLiveData<List<Task>> = MutableLiveData(null)
    override val selectedTaskStatus: MutableLiveData<Int> = MutableLiveData(TaskStatus.CREATED)

    private var allTasks: List<Task>? = null

    override val selectedGroup: MutableLiveData<Group> = MutableLiveData()

    private fun loadGroups() = launchWithLoader {
        groups.value = null
        when (val result = groupRepository.getAllGroups()) {
            is RepositoryResult.Success -> {
                if(selectedGroup.value == null)
                    selectedGroup.value = result.data.firstOrNull()
                groups.value = result.data
                loadTasks()
            }
            is RepositoryResult.Error -> showError(result.error)
        }
    }

    override fun groupSelected(group: Group) {
        selectedGroup.value = group
        loadTasks()
    }

    private fun loadTasks() = launchWithLoader {
        allTasks = null
        tasks.value = null
        val selectedGroup = selectedGroup.value ?: return@launchWithLoader
        when (val result = taskRepository.getTasks(selectedGroup)) {
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

    override fun taskStatusSelected(taskStatus: Int) {
        selectedTaskStatus.value = taskStatus
        setFilteredTasks()
    }

    override fun removeTask(task: Task) = launchWithLoader {
        when(val result = taskRepository.removeTask(task)) {
            is RepositoryResult.Success -> {
                allTasks = allTasks?.filter { it != task }
                setFilteredTasks()
            }
            is RepositoryResult.Error -> showErrorMessage(result.error)
        }
    }

    override fun getSharedTasks(task: Task): List<Task> {
        return allTasks?.filter { it.sharedTaskId == task.sharedTaskId } ?: listOf()
    }

    override fun reloadData() {
        super.reloadData()
        loadGroups()
    }


}