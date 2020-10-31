package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.util.*

data class TaskApiModel(
        @SerializedName("id")
        override val id: Long,
        @SerializedName("createDate")
        val createDate: Instant,
        @SerializedName("assignDate")
        val assignDate: Instant?,
        @SerializedName("deadline")
        val deadline: Instant,
        @SerializedName("startDate")
        val startDate: Instant?,
        @SerializedName("description")
        val description: String,
        @SerializedName("estimatedExecutionTimeInMillis")
        val estimatedExecutionTimeInMillis: Long,
        @SerializedName("groupId")
        val groupId: Long,
        @SerializedName("localizationId")
        val localizationId: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("isSubTask")
        val isSubTask: Boolean,
        @SerializedName("sharedTaskId")
        val sharedTaskId: UUID,
        @SerializedName("workerId")
        val workerId: Long?,
        @SerializedName("taskWorkerReportView")
        val taskWorkerReportApiModel: TaskWorkerReportApiModel?,
        @SerializedName("taskManagerReportView")
        val taskManagerReportModel: TaskManagerReportApiModel?,
): IdentifiableApiModel