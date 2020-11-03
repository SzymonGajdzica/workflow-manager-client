package pl.polsl.workflow.manager.client.ui.coordinator.group.patch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.User

class GroupCoordinatorPatchListAdapter(
        private val actionButtonClickListener: (Int) -> Unit
): RecyclerView.Adapter<GroupCoordinatorPatchViewHolder>() {

    private val workers: ArrayList<User> = arrayListOf()

    fun updateList(workers: List<User>) {
        this.workers.clear()
        this.workers.addAll(workers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupCoordinatorPatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_coordinator_post_worker_list_item, parent, false)
        return GroupCoordinatorPatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupCoordinatorPatchViewHolder, position: Int) {
        holder.updateViews(workers[position], position, actionButtonClickListener)
    }

    override fun getItemCount(): Int = workers.size


}