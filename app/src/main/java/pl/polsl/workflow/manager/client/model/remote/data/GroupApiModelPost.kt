package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName

data class GroupApiModelPost(
    @SerializedName("name")
    val name: String,
    @SerializedName("managerId")
    val managerId: Long?,
)
