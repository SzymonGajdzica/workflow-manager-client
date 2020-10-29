package pl.polsl.workflow.manager.client.model.remote.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pl.polsl.workflow.manager.client.model.data.*
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.model.remote.api.TaskApi
import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.data.TaskApiModel
import pl.polsl.workflow.manager.client.model.remote.mapper.map
import pl.polsl.workflow.manager.client.model.remote.safeCall

class TaskRepositoryImpl(
    private val taskApi: TaskApi,
    private val localizationApi: LocalizationApi,
    private val userApi: UserApi
) : TaskRepository {

    override suspend fun getCurrentTask(): RepositoryResult<Task> {
        return getTask { taskApi.getCurrentTask() }
    }

    override suspend fun getNextTask(autoStart: Boolean): RepositoryResult<Task> {
        return getTask { taskApi.getNextTask(autoStart) }
    }

    private suspend fun getTask(pendingTaskModel: suspend () -> TaskApiModel): RepositoryResult<Task> = safeCall {
        val localizationsPending = GlobalScope.async { localizationApi.getAllLocalizations() }
        val usersPending = GlobalScope.async { userApi.getAllUsers() }
        pendingTaskModel().map(null, localizationsPending.await().toMap(), usersPending.await().toMap())
    }

    override suspend fun sendManagerReport(taskManagerReportPost: TaskManagerReportPost): RepositoryResult<TaskManagerReport> = safeCall {
        taskApi.acceptTask(taskManagerReportPost.map()).map()
    }

    override suspend fun sendTaskReport(taskWorkerReportPost: TaskWorkerReportPost): RepositoryResult<TaskWorkerReport> = safeCall {
        taskApi.finishTask(taskWorkerReportPost.map()).map()
    }

    override suspend fun addTask(taskPost: TaskPost): RepositoryResult<Task> = safeCall {
        val localizationsPending = GlobalScope.async { localizationApi.getAllLocalizations() }
        val tasksPending = GlobalScope.async { taskApi.getTasks(taskPost.group.id) }
        val usersPending = GlobalScope.async { userApi.getAllUsers() }
        taskApi.addTask(taskPost.map()).map(
                tasks = tasksPending.await().toMap(),
                localizations = localizationsPending.await().toMap(),
                users = usersPending.await().toMap()
        )
    }

    override suspend fun removeTask(task: Task): RepositoryResult<Unit> = safeCall {
        taskApi.removeTask(task.id)
    }

    override suspend fun getTasks(group: Group): RepositoryResult<List<Task>> = safeCall {
        val localizationsPending = GlobalScope.async { localizationApi.getAllLocalizations() }
        val usersPending = GlobalScope.async { userApi.getAllUsers() }
        val tasks = taskApi.getTasks(group.id)
        val tasksMap = tasks.toMap()
        val localizationsMap = localizationsPending.await().toMap()
        tasks.map { task ->
            task.map(
                    tasks = tasksMap,
                    localizations = localizationsMap,
                    users = usersPending.await().toMap()
            )
        }
    }

}