package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName

data class UserApiModel(
        @SerializedName("id")
        override val id: Long,
        @SerializedName("username")
        val username: String,
        @SerializedName("role")
        val role: String,
        @SerializedName("enabled")
        val enabled: Boolean,
): IdentifiableApiModel