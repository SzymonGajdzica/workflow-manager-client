package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskWorkerReportPost(
        val description: String,
        val success: Boolean,
        val task: Task,
): Parcelable