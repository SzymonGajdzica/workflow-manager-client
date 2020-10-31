package pl.polsl.workflow.manager.client.ui.manager.task

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_manager_task_list_item.view.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskStatus
import pl.polsl.workflow.manager.client.model.data.status

class TaskManagerViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    private val taskName: TextView = view.managerTaskListItemName
    private val actionButton: ImageButton = view.managerTaskListItemActionButton

    fun updateViews(task: Task, position: Int, itemClickListener: (Int) -> Unit, actionButtonClickListener: (Int) -> Unit) {
        taskName.text = task.name
        actionButton.visibility = View.VISIBLE
        when(task.status) {
            TaskStatus.CREATED -> actionButton.setImageResource(R.drawable.ic_baseline_delete_24)
            TaskStatus.FINISHED -> actionButton.setImageResource(R.drawable.ic_baseline_assignment_24)
            else -> actionButton.visibility = View.GONE
        }
        view.setOnClickListener {
            itemClickListener(position)
        }
        actionButton.setOnClickListener {
            actionButtonClickListener(position)
        }
    }


}