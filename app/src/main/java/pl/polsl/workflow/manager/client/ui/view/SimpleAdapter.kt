package pl.polsl.workflow.manager.client.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter(
        @LayoutRes private val viewId: Int,
        private val list: List<List<String>>,
        private val onClick: (Int) -> Unit
): RecyclerView.Adapter<SimpleAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewId, parent, false)
        return SimpleAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleAdapterViewHolder, position: Int) {
        holder.update(list[position], position, onClick)
    }

    override fun getItemCount(): Int = list.size

}