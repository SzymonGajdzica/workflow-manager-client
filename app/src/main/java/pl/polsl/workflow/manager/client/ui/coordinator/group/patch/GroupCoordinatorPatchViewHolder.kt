package pl.polsl.workflow.manager.client.ui.coordinator.group.patch

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.base_list_item_with_action_button.view.*
import pl.polsl.workflow.manager.client.model.data.User

class GroupCoordinatorPatchViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val username: TextView = view.listItemTitle
    private val actionButton: ImageButton = view.listItemActionButton

    fun updateViews(worker: User, position: Int, actionButtonClickListener: (Int) -> Unit) {
        username.text = worker.username
        actionButton.setOnClickListener {
            actionButtonClickListener(position)
        }
    }


}