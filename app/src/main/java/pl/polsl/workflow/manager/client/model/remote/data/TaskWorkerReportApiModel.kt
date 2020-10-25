package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class TaskWorkerReportApiModel(
        @SerializedName("id")
        val id: Long,
        @SerializedName("date")
        val date: Instant,
        @SerializedName("description")
        val description: String,
        @SerializedName("success")
        val success: Boolean,
)