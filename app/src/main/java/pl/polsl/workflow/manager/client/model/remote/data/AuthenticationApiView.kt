package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class AuthenticationApiView(
    @SerializedName("token")
    val token: String,
    @SerializedName("expirationDate")
    val expirationDate: Date,
)