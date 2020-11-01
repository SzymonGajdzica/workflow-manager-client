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

    private var allTasks: List<Task>? = null

    private var selectedTaskStatus = TaskStatus.CREATED

    override val selectedGroup: MutableLiveData<Group> = MutableLiveData()

    private fun loadGroups() = launchWithLoader {
        groups.value = null
        when (val result = groupRepository.getAllGroups()) {
            is RepositoryResult.Success -> {
                groups.value = result.data
                selectedGroup.value = result.data.firstOrNull()
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
        tasks.value = allTasks.filter { it.status == selectedTaskStatus }.sortedBy { it.deadline }
    }

    override fun taskStatusSelected(taskStatus: Int) {
        this.selectedTaskStatus = taskStatus
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