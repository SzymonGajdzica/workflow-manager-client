package pl.polsl.workflow.manager.client.model.remote.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReport
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReportPost
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.model.remote.api.TaskApi
import pl.polsl.workflow.manager.client.model.remote.data.TaskApiModel
import pl.polsl.workflow.manager.client.model.remote.mapper.map
import pl.polsl.workflow.manager.client.model.remote.safeCall
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskApi: TaskApi,
    private val localizationApi: LocalizationApi
) : TaskRepository {

    override suspend fun getCurrentTask(): RepositoryResult<Task> {
        return getTask { taskApi.getCurrentTask() }
    }

    override suspend fun getNextTask(autoStart: Boolean): RepositoryResult<Task> {
        return getTask { taskApi.getNextTask(autoStart) }
    }

    private suspend fun getTask(pendingTaskModel: suspend () -> TaskApiModel): RepositoryResult<Task> = safeCall {
        val localizationsPending = GlobalScope.async { localizationApi.getAllLocalizations() }
        val taskPending = GlobalScope.async { pendingTaskModel() }
        taskPending.await().map(null, localizationsPending.await().map { it.id to it }.toMap())
    }

    override suspend fun sendTaskReport(taskWorkerReportPost: TaskWorkerReportPost): RepositoryResult<TaskWorkerReport> = safeCall {
        taskApi.finishTask(taskWorkerReportPost.map()).map()
    }

}