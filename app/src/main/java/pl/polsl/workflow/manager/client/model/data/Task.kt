package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.polsl.workflow.manager.client.model.Identifiable
import java.time.Instant

@Parcelize
data class Task(
        override val id: Long,
        val name: String,
        val createDate: Instant,
        val assignDate: Instant?,
        val deadline: Instant,
        val startDate: Instant?,
        val description: String,
        val estimatedExecutionTime: Instant,
        val localization: Localization,
        val superTask: Task?,
        val assignedWorker: User?,
        val taskWorkerReport: TaskWorkerReport?,
        val taskManagerReport: TaskManagerReport?,
): Parcelable, Identifiable