package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.polsl.workflow.manager.client.model.Identifiable

@Parcelize
data class Group(
    override val id: Long,
    val name: String,
    val manager: User,
    val workers: List<User>
): Parcelable, Identifiable
