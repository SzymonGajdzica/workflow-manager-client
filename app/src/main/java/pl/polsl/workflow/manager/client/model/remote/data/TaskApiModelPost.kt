package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class TaskApiModelPost(
        @SerializedName("autoAssign")
        val autoAssign: Boolean,
        @SerializedName("deadline")
        val deadline: Instant?,
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
        @SerializedName("subTaskId")
        val subTaskId: Long?,
        @SerializedName("workerId")
        val workerId: Long?,
)