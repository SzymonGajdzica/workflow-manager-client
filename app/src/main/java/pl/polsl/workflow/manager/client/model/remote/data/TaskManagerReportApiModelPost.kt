package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName

data class TaskManagerReportApiModelPost(
        @SerializedName("description")
        val description: String,
        @SerializedName("taskId")
        val taskId: Long?,
)