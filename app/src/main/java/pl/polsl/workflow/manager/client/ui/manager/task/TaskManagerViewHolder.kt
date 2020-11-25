package pl.polsl.workflow.manager.client.ui.manager.task

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskStatus
import pl.polsl.workflow.manager.client.model.data.status
import pl.polsl.workflow.manager.client.ui.view.setClickableBackground

class TaskManagerViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    private val taskName: TextView = view.findViewById(R.id.listItemTitle)
    private val actionButton: ImageButton = view.findViewById(R.id.listItemActionButton)

    fun updateViews(task: Task, position: Int, itemClickListener: (Int) -> Unit, actionButtonClickListener: (Int) -> Unit) {
        taskName.text = task.name
        actionButton.visibility = View.VISIBLE
        when(task.status) {
            TaskStatus.CREATED -> actionButton.setImageResource(R.drawable.ic_baseline_delete_24)
            TaskStatus.FINISHED -> actionButton.setImageResource(R.drawable.ic_baseline_assignment_24)
            else -> actionButton.visibility = View.GONE
        }
        view.setClickableBackground()
        view.setOnClickListener {
            itemClickListener(position)
        }
        actionButton.setOnClickListener {
            actionButtonClickListener(position)
        }
    }


}