package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName

data class GroupApiModelPatch(
    @SerializedName("name")
    val name: String?,
    @SerializedName("managerId")
    val managerId: Long?,
    @SerializedName("workerIds")
    val workerIds: List<Long>?,
)
