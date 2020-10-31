package pl.polsl.workflow.manager.client.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter(
        @LayoutRes private val viewId: Int,
        private val onClick: ((Int) -> Unit)?
): RecyclerView.Adapter<SimpleAdapterViewHolder>() {

    private val list: ArrayList<ArrayList<String>> = arrayListOf()

    fun updateSingleList(newList: List<String>) {
        updateList(newList.map { listOf(it) })
    }

    private fun updateList(newList: List<List<String>>) {
        list.clear()
        list.addAll(newList.map { ArrayList(it) })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewId, parent, false)
        return SimpleAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleAdapterViewHolder, position: Int) {
        holder.update(list[position], position, onClick)
    }

    override fun getItemCount(): Int = list.size

}