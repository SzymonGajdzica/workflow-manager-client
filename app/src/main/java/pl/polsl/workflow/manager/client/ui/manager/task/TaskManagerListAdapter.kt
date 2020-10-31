package pl.polsl.workflow.manager.client.ui.manager.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.Task

class TaskManagerListAdapter(
        private val itemClickListener: (Int) -> Unit,
        private val actionButtonClickListener: (Int) -> Unit
): RecyclerView.Adapter<TaskManagerViewHolder>() {

    private val tasks: ArrayList<Task> = arrayListOf()

    fun updateList(tasks: List<Task>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskManagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_manager_task_list_item, parent, false)
        return TaskManagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskManagerViewHolder, position: Int) {
        holder.updateViews(tasks[position], position, itemClickListener, actionButtonClickListener)
    }

    override fun getItemCount(): Int = tasks.size


}