package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskManagerReportPost(
        val description: String,
        val task: Task,
): Parcelable