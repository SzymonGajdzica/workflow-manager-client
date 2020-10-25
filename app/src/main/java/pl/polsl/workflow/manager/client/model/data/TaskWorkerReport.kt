package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.Instant

@Parcelize
data class TaskWorkerReport(
    val id: Long,
    val date: Instant,
    val description: String,
    val success: Boolean,
): Parcelable