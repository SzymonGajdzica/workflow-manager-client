package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.Instant
import java.util.*

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
        val sharedTaskId: UUID,
        val isSubTask: Boolean,
        val assignedWorker: User?,
        val group: Group,
        val taskWorkerReport: TaskWorkerReport?,
        val taskManagerReport: TaskManagerReport?,
): Parcelable, Identifiable