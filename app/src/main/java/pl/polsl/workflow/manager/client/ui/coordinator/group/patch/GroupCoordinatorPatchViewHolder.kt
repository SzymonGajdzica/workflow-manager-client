package pl.polsl.workflow.manager.client.ui.coordinator.group.patch

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.User

class GroupCoordinatorPatchViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val username: TextView = view.findViewById(R.id.listItemTitle)
    private val actionButton: ImageButton = view.findViewById(R.id.listItemActionButton)

    fun updateViews(worker: User, position: Int, actionButtonClickListener: (Int) -> Unit) {
        actionButton.setImageResource(R.drawable.ic_baseline_delete_24)
        username.text = worker.username
        actionButton.setOnClickListener {
            actionButtonClickListener(position)
        }
    }


}