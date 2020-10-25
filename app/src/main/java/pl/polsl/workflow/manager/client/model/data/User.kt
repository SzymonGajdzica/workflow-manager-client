package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.polsl.workflow.manager.client.model.Identifiable

@Parcelize
data class User(
        override val id: Long,
        val username: String,
        val role: Int,
        val enabled: Boolean,
): Parcelable, Identifiable