package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName

data class TaskWorkerReportApiModelPost(
        @SerializedName("description")
        val description: String,
        @SerializedName("success")
        val success: Boolean,
        @SerializedName("taskId")
        val taskId: Long,
)