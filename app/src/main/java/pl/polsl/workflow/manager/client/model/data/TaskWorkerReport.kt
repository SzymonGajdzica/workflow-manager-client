package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.polsl.workflow.manager.client.model.Identifiable
import java.time.Instant

@Parcelize
data class TaskWorkerReport(
        override val id: Long,
        val date: Instant,
        val description: String,
        val success: Boolean,
): Parcelable, Identifiable