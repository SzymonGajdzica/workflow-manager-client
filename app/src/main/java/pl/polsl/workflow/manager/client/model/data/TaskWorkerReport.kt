package pl.polsl.workflow.manager.client.model.data

import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class TaskWorkerReport(
        override val id: Long,
        val date: Instant,
        val description: String,
        val success: Boolean,
): Identifiable