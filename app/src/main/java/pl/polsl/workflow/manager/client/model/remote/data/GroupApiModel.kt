package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName
import pl.polsl.workflow.manager.client.model.Identifiable

data class GroupApiModel(
    @SerializedName("id")
    override val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("managerId")
    val managerId: Long,
    @SerializedName("workerIdList")
    val workerIdList: List<Long>,
): Identifiable
