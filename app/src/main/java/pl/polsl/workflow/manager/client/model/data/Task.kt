package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.polsl.workflow.manager.client.model.Identifiable
import java.time.Instant
import java.util.*

@Parcelize
data class Task(
        override val id: Long,
        val autoAssign: Boolean,
        val createDate: Instant,
        val assignDate: Instant?,
        val deadline: Instant,
        val startDate: Instant?,
        val description: String,
        val estimatedExecutionTime: Instant,
        val groupId: Long,
        val localization: Localization,
        val name: String,
        val sharedTaskId: UUID,
        val workerId: Long?,
        val taskWorkerReport: TaskWorkerReport?,
        val taskManagerReport: TaskManagerReport?,
): Parcelable, Identifiable