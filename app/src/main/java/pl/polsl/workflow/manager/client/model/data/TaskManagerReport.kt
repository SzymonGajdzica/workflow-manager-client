package pl.polsl.workflow.manager.client.model.data

import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class TaskManagerReport(
        override val id: Long,
        val date: Instant,
        val description: String,
        val fixTask: AllowableValue<Task>?,
): Identifiable