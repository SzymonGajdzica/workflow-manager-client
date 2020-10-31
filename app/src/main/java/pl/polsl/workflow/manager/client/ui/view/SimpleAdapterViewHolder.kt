package pl.polsl.workflow.manager.client.ui.view

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapterViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    private val textViews: Array<TextView> = (view as ViewGroup)
            .children
            .mapNotNull { it as? TextView }
            .toList()
            .toTypedArray()

    fun update(list: List<String>, position: Int, onClick: ((Int) -> Unit)?) {
        list.forEachIndexed { index, text ->
            textViews[index].text = text
        }
        if(onClick != null) {
            TypedValue().also { outValue ->
                view.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                view.setBackgroundResource(outValue.resourceId)
            }
            view.isClickable = true
            view.isFocusable = true
            view.setOnClickListener {
                onClick(position)
            }
        }

    }

}