package pl.polsl.workflow.manager.client.ui.coordinator.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.User

class AccountCoordinatorListAdapter(
        private val actionButtonClickListener: (Int) -> Unit
): RecyclerView.Adapter<AccountCoordinatorViewHolder>() {

    private val users: ArrayList<User> = arrayListOf()

    fun updateList(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountCoordinatorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_coordinator_user_list_item, parent, false)
        return AccountCoordinatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountCoordinatorViewHolder, position: Int) {
        holder.updateViews(users[position], position, actionButtonClickListener)
    }

    override fun getItemCount(): Int = users.size


}