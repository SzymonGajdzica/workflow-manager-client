package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class Authentication(
    val token: String,
    val expirationDate: Instant,
): Parcelable