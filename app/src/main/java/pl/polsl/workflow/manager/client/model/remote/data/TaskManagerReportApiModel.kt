package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class TaskManagerReportApiModel(
        @SerializedName("id")
        override val id: Long,
        @SerializedName("date")
        val date: Instant,
        @SerializedName("description")
        val description: String,
        @SerializedName("fixTaskId")
        val fixTaskId: Long?,
): IdentifiableApiModel