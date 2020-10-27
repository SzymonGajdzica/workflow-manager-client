package pl.polsl.workflow.manager.client.model.data

import android.content.Context
import pl.polsl.workflow.manager.client.R

object TaskState {

    const val CREATED = 0
    const val STARTED = 1
    const val FINISHED = 2
    const val ACCEPTED = 3

    val all: List<Int> = arrayListOf(CREATED, STARTED, FINISHED, ACCEPTED)

    fun convertToString(context: Context, taskState: Int): String {
        return when(taskState) {
            CREATED -> context.getString(R.string.created)
            STARTED -> context.getString(R.string.started)
            FINISHED -> context.getString(R.string.finished)
            ACCEPTED -> context.getString(R.string.accepted)
            else -> throw IllegalArgumentException()
        }
    }

}