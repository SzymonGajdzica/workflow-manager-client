package pl.polsl.workflow.manager.client.model.remote.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pl.polsl.workflow.manager.client.model.data.*
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.api.GroupApi
import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.model.remote.api.TaskApi
import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.data.GroupApiModel
import pl.polsl.workflow.manager.client.model.remote.data.TaskApiModel
import pl.polsl.workflow.manager.client.model.remote.mapper.map
import pl.polsl.workflow.manager.client.model.remote.safeCall

class TaskRepositoryImpl(
    private val taskApi: TaskApi,
    private val localizationApi: LocalizationApi,
    private val userApi: UserApi,
    private val groupApi: GroupApi
) : TaskRepository {

    override suspend fun getCurrentTask(): RepositoryResult<Task> {
        return getTask { taskApi.getCurrentTask() }
    }

    override suspend fun getNextTask(autoStart: Boolean): RepositoryResult<Task> {
        return getTask { taskApi.getNextTask(autoStart) }
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
        val groupsPending = GlobalScope.async { groupApi.getAllGroups() }
        taskApi.addTask(taskPost.map()).map(
            tasks = tasksPending.await().toMap(),
            localizations = localizationsPending.await().toMap(),
            users = usersPending.await().toMap(),
            groups = groupsPending.await().toMap()
        )
    }

    override suspend fun removeTask(task: Task): RepositoryResult<Unit> = safeCall {
        taskApi.removeTask(task.id)
    }

    override suspend fun getTasks(group: Group): RepositoryResult<List<Task>> {
        return getTasks( { taskApi.getTasks(group.id) }, { groupApi.getAllGroups() })
    }

    override suspend fun getWorkerTasks(): RepositoryResult<List<Task>> {
        return getTasks({ taskApi.getWorkerTasks() }, { listOf(groupApi.getWorkerGroup()) })
    }

    private suspend fun getTasks(pendingTaskModels: suspend () -> List<TaskApiModel>, pendingGroupModels: suspend () -> List<GroupApiModel>): RepositoryResult<List<Task>> = safeCall {
        val localizationsPending = GlobalScope.async { localizationApi.getAllLocalizations() }
        val usersPending = GlobalScope.async { userApi.getAllUsers() }
        val groupsPending = GlobalScope.async { pendingGroupModels() }
        val tasks = pendingTaskModels()
        val tasksMap = tasks.toMap()
        val localizationsMap = localizationsPending.await().toMap()
        val groupsMap = groupsPending.await().toMap()
        val usersMap = usersPending.await().toMap()
        tasks.map { task ->
            task.map(
                    tasks = tasksMap,
                    localizations = localizationsMap,
                    users = usersMap,
                    groups = groupsMap
            )
        }
    }

    private suspend fun getTask(pendingTaskModel: suspend () -> TaskApiModel): RepositoryResult<Task> = safeCall {
        val localizationsPending = GlobalScope.async { localizationApi.getAllLocalizations() }
        val usersPending = GlobalScope.async { userApi.getAllUsers() }
        val groupsPending = GlobalScope.async { listOf(groupApi.getWorkerGroup()) }
        pendingTaskModel().map(null, localizationsPending.await().toMap(), usersPending.await().toMap(), groupsPending.await().toMap())
    }

}