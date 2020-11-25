package pl.polsl.workflow.manager.client.model.data

import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.util.*

@Parcelize
data class Task(
        override val id: Long,
        val name: String,
        val description: String,
        val createDate: Instant,
        val deadline: Instant,
        val estimatedExecutionTime: Instant,
        val assignDate: Instant?,
        val startDate: Instant?,
        val localization: Localization,
        val sharedTaskId: UUID,
        val isSubTask: Boolean,
        val assignedWorker: User?,
        val group: Group,
        val taskWorkerReport: TaskWorkerReport?,
        val taskManagerReport: TaskManagerReport?,
): Identifiable