package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.data.*
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList

interface TaskDataSource {

    suspend fun getCurrentTask(): TaskApiModel

    suspend fun getNextTask(autoStart: Boolean): TaskApiModel

    suspend fun finishTask(taskWorkerReportApiModelPost: TaskWorkerReportApiModelPost): TaskWorkerReportApiModel

    suspend fun acceptTask(taskManagerReportApiModelPost: TaskManagerReportApiModelPost): TaskManagerReportApiModel

    suspend fun addTask(taskApiModelPost: TaskApiModelPost): TaskApiModel

    suspend fun removeTask(taskId: Long)

    fun getTasks(groupId: Long): LazyList<TaskApiModel>

    fun getWorkerTasks(): LazyList<TaskApiModel>

}