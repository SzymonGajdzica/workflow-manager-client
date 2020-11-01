package pl.polsl.workflow.manager.client.model.repository

import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.*
import pl.polsl.workflow.manager.client.model.mapper.map
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModel
import pl.polsl.workflow.manager.client.model.remote.data.TaskApiModel
import pl.polsl.workflow.manager.client.model.remote.source.GroupDataSource
import pl.polsl.workflow.manager.client.model.remote.source.LocalizationDataSource
import pl.polsl.workflow.manager.client.model.remote.source.TaskDataSource
import pl.polsl.workflow.manager.client.model.remote.source.UserDataSource
import pl.polsl.workflow.manager.client.model.safeCall
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList

class TaskRepositoryImpl(
    private val taskDataSource: TaskDataSource,
    private val localizationDataSource: LocalizationDataSource,
    private val userDataSource: UserDataSource,
    private val groupDataSource: GroupDataSource
) : TaskRepository {

    private suspend fun TaskApiModel.mMap(tasks: LazyList<TaskApiModel>?, groups: LazyList<GroupApiModel>): Task {
        return map(
                tasks = tasks,
                localizations = localizationDataSource.getAllLocalizations(),
                users = userDataSource.getAllUsers(),
                groups = groups
        )
    }

    override suspend fun getCurrentTask(): RepositoryResult<Task> = safeCall {
        taskDataSource.getCurrentTask().mMap(null, groupDataSource.getWorkerGroup())
    }

    override suspend fun getNextTask(autoStart: Boolean): RepositoryResult<Task> = safeCall {
        taskDataSource.getNextTask(autoStart).mMap(null, groupDataSource.getWorkerGroup())
    }

    override suspend fun sendManagerReport(taskManagerReportPost: TaskManagerReportPost): RepositoryResult<TaskManagerReport> = safeCall {
        taskDataSource.acceptTask(taskManagerReportPost.map()).map()
    }

    override suspend fun sendTaskReport(taskWorkerReportPost: TaskWorkerReportPost): RepositoryResult<TaskWorkerReport> = safeCall {
        taskDataSource.finishTask(taskWorkerReportPost.map()).map()
    }

    override suspend fun addTask(taskPost: TaskPost): RepositoryResult<Task> = safeCall {
        taskDataSource.addTask(taskPost.map()).mMap(taskDataSource.getTasks(taskPost.group.id), groupDataSource.getAllGroups())
    }

    override suspend fun removeTask(task: Task): RepositoryResult<Unit> = safeCall {
        taskDataSource.removeTask(task.id)
    }

    override suspend fun getTasks(group: Group): RepositoryResult<List<Task>> = safeCall {
        taskDataSource.getTasks(group.id).getAll().map {
            it.mMap(taskDataSource.getTasks(group.id), groupDataSource.getAllGroups())
        }
    }

    override suspend fun getWorkerTasks(): RepositoryResult<List<Task>> = safeCall {
        taskDataSource.getWorkerTasks().getAll().map {
            it.mMap(taskDataSource.getWorkerTasks(), groupDataSource.getWorkerGroup())
        }
    }

}