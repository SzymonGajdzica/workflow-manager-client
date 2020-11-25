package pl.polsl.workflow.manager.client.ui.coordinator.account

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.User

class AccountCoordinatorViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val username: TextView = view.findViewById(R.id.listItemTitle)
    private val actionButton: ImageButton = view.findViewById(R.id.listItemActionButton)

    fun updateViews(user: User, position: Int, actionButtonClickListener: (Int) -> Unit) {
        username.text = user.username
        val resId = if(user.enabled)
            R.drawable.ic_baseline_visibility_24
        else
            R.drawable.ic_baseline_visibility_off_24
        actionButton.setImageResource(resId)
        actionButton.setOnClickListener {
            actionButtonClickListener(position)
        }
    }


}