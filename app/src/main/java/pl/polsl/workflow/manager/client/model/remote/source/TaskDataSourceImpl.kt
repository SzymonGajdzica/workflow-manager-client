package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.api.TaskApi
import pl.polsl.workflow.manager.client.model.remote.data.*
import pl.polsl.workflow.manager.client.model.unwrap
import pl.polsl.workflow.manager.client.util.lazy.list.ExpirableCachedLazyList
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList
import java.time.Instant

class TaskDataSourceImpl(
        private val taskApi: TaskApi
): TaskDataSource {

    private val workerLazyList: LazyList<TaskApiModel> = ExpirableCachedLazyList(Instant.ofEpochSecond(30L * 60L)) {
        taskApi.getWorkerTasks()
    }

    private val managerLazyLists: HashMap<Long, LazyList<TaskApiModel>> = hashMapOf()

    private fun getManagerLazyList(groupId: Long): LazyList<TaskApiModel> {
        return managerLazyLists[groupId] ?: ExpirableCachedLazyList(Instant.ofEpochSecond(30L * 60L)) {
            taskApi.getTasks(groupId)
        }.also { managerLazyLists[groupId] = it }
    }

    override suspend fun getCurrentTask(): TaskApiModel {
        return taskApi.getCurrentTask()
    }

    override suspend fun getNextTask(autoStart: Boolean): TaskApiModel {
        return taskApi.getNextTask(autoStart)
    }

    override suspend fun finishTask(taskWorkerReportApiModelPost: TaskWorkerReportApiModelPost): TaskWorkerReportApiModel {
        return taskApi.finishTask(taskWorkerReportApiModelPost).also {
            workerLazyList.clear()
        }
    }

    override suspend fun acceptTask(taskManagerReportApiModelPost: TaskManagerReportApiModelPost): TaskManagerReportApiModel {
        return taskApi.acceptTask(taskManagerReportApiModelPost).also {
            managerLazyLists.clear()
        }
    }

    override suspend fun addTask(taskApiModelPost: TaskApiModelPost): TaskApiModel {
        return taskApi.addTask(taskApiModelPost).also {
            getManagerLazyList(it.groupId).supplyItem(it)
        }
    }

    override suspend fun removeTask(taskId: Long) {
        taskApi.removeTask(taskId).unwrap().also {
            managerLazyLists.clear()
        }
    }

    override fun getTasks(groupId: Long): LazyList<TaskApiModel> {
        return getManagerLazyList(groupId)
    }

    override fun getWorkerTasks(): LazyList<TaskApiModel> {
        return workerLazyList
    }
}