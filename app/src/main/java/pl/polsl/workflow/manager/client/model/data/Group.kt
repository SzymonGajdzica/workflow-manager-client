package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group(
    val id: Long,
    val name: String,
    val manager: User,
    val workers: List<User>
): Parcelable
