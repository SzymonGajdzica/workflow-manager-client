package pl.polsl.workflow.manager.client.model.remote.api

import pl.polsl.workflow.manager.client.model.remote.data.TaskApiModel
import pl.polsl.workflow.manager.client.model.remote.data.TaskWorkerReportApiModel
import pl.polsl.workflow.manager.client.model.remote.data.TaskWorkerReportApiModelPost
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TaskApi {

    @GET("task/current/")
    suspend fun getCurrentTask(): TaskApiModel

    @GET("task/next/")
    suspend fun getNextTask(@Query("autoStart") autoStart: Boolean): TaskApiModel

    @POST("taskWorkerReport/")
    suspend fun finishTask(@Body taskWorkerReportApiModelPost: TaskWorkerReportApiModelPost): TaskWorkerReportApiModel

}