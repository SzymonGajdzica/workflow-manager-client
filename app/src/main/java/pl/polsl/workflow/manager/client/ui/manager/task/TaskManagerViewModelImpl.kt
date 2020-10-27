package pl.polsl.workflow.manager.client.ui.manager.task

import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.model.data.Group
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskState
import pl.polsl.workflow.manager.client.model.data.state
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.GroupRepository
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepository
import javax.inject.Inject

class TaskManagerViewModelImpl @Inject constructor(
    app: Application,
    private val taskRepository: TaskRepository,
    private val groupRepository: GroupRepository
): TaskManagerViewModel(app) {

    override val groups: MutableLiveData<List<Group>> = MutableLiveData()
    override val tasks: MutableLiveData<List<Task>> = MutableLiveData(null)

    private var allTasks: List<Task>? = null

    private var selectedTaskState = TaskState.CREATED

    override val selectedGroup: MutableLiveData<Group> = MutableLiveData()

    private fun loadGroups() {
        launchWithLoader {
            groups.value = null
            when(val result = groupRepository.getAllGroups()) {
                is RepositoryResult.Success -> {
                    groups.value = result.data
                    selectedGroup.value = result.data.firstOrNull()
                    loadTasks()
                }
                is RepositoryResult.Error -> showError(result.error)
            }
        }
    }

    override fun groupSelected(group: Group) {
        selectedGroup.value = group
        loadTasks()
    }

    private fun loadTasks() {
        allTasks = null
        tasks.value = null
        val selectedGroup = selectedGroup.value ?: return
        launchWithLoader {
            when(val result = taskRepository.getTasks(selectedGroup)) {
                is RepositoryResult.Success -> {
                    allTasks = result.data
                    setFilteredTasks()
                }
                is RepositoryResult.Error -> showError(result.error)
            }
        }
    }

    private fun setFilteredTasks() {
        val allTasks = allTasks ?: return
        tasks.value = allTasks.filter { it.state == selectedTaskState }.sortedBy { it.deadline }
    }

    override fun taskStatusSelected(taskState: Int) {
        this.selectedTaskState = taskState
        setFilteredTasks()
    }

    override fun reloadData() {
        super.reloadData()
        if(groups.value == null)
            loadGroups()
        else
            loadTasks()
    }


}