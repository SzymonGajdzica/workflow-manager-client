package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName

data class UserApiModelPatch(
        @SerializedName("enabled")
        val enabled: Boolean,
)