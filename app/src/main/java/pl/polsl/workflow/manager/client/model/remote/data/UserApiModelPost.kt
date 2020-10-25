package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName

data class UserApiModelPost(
        @SerializedName("username")
        val username: String,
        @SerializedName("role")
        val role: String,
        @SerializedName("password")
        val password: String,
)