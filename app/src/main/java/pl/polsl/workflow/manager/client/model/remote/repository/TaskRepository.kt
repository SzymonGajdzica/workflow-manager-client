package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReport
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReportPost
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult

interface TaskRepository {

    suspend fun getCurrentTask(): RepositoryResult<Task>

    suspend fun getNextTask(autoStart: Boolean): RepositoryResult<Task>

    suspend fun sendTaskReport(taskWorkerReportPost: TaskWorkerReportPost): RepositoryResult<TaskWorkerReport>

}