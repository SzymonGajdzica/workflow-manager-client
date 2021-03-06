package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.*
import retrofit2.Response
import retrofit2.http.*

interface TaskApi {

    @GET("task/current/")
    suspend fun getCurrentTask(): TaskApiModel

    @GET("task/next/")
    suspend fun getNextTask(@Query("autoStart") autoStart: Boolean): TaskApiModel

    @POST("taskWorkerReport/")
    suspend fun finishTask(@Body taskWorkerReportApiModelPost: TaskWorkerReportApiModelPost): TaskWorkerReportApiModel

    @POST("taskManagerReport/")
    suspend fun acceptTask(@Body taskManagerReportApiModelPost: TaskManagerReportApiModelPost): TaskManagerReportApiModel

    @POST("task/")
    suspend fun addTask(@Body taskApiModelPost: TaskApiModelPost): TaskApiModel

    @DELETE("task/{taskId}/")
    suspend fun removeTask(@Path("taskId") taskId: Long): Response<Unit>

    @GET("task/manager/{groupId}/")
    suspend fun getTasks(@Path("groupId") groupId: Long): List<TaskApiModel>

    @GET("task/worker/")
    suspend fun getWorkerTasks(): List<TaskApiModel>


}