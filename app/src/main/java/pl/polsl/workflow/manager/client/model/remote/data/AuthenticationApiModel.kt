package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class AuthenticationApiModel(
    @SerializedName("token")
    val token: String,
    @SerializedName("expirationDate")
    val expirationDate: Instant,
)