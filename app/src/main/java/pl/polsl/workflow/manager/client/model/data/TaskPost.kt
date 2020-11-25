package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class TaskPost(
    val deadline: Instant,
    val description: String,
    val estimatedExecutionTime: Instant,
    val group: Group,
    val localization: Localization,
    val name: String,
    val worker: User?,
    val subTask: Task?
): Parcelable