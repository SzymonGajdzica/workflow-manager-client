package pl.polsl.workflow.manager.client.model.repository

import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.*

interface TaskRepository {

    suspend fun getCurrentTask(): RepositoryResult<Task>

    suspend fun getNextTask(autoStart: Boolean): RepositoryResult<Task>

    suspend fun sendManagerReport(taskManagerReportPost: TaskManagerReportPost): RepositoryResult<TaskManagerReport>

    suspend fun sendTaskReport(taskWorkerReportPost: TaskWorkerReportPost): RepositoryResult<TaskWorkerReport>

    suspend fun addTask(taskPost: TaskPost): RepositoryResult<Task>

    suspend fun removeTask(task: Task): RepositoryResult<Unit>

    suspend fun getTasks(group: Group): RepositoryResult<List<Task>>

    suspend fun getWorkerTasks(): RepositoryResult<List<Task>>

}