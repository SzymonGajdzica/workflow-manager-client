package pl.polsl.workflow.manager.client.ui.view

import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.util.extension.getActivity
import pl.polsl.workflow.manager.client.util.extension.hideKeyboard
import pl.polsl.workflow.manager.client.util.extension.indexOfOrNull
import java.time.Instant
import java.time.ZoneOffset

fun RecyclerView.setupSimpleAdapter(
        @LayoutRes viewId: Int = R.layout.base_list_item,
        onClick: ((Int) -> Unit)? = null
) {
    setupAdapter(SimpleAdapter(viewId, onClick))
}

fun RecyclerView.setupAdapter(mAdapter: RecyclerView.Adapter<*>) {
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context)
    addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    adapter = mAdapter
}

fun MaterialAutoCompleteTextView.mSetOnItemSelectedListener(editable: Boolean = false, callback: (Int) -> Unit) {
    onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
        val realPosition = allElements.indexOfOrNull(adapter?.getItem(position)) ?: position
        callback(realPosition)
        if(editable) {
            context?.getActivity()?.hideKeyboard()
            clearFocus()
        }
    }
    if(!editable) {
        setOnDismissListener {
            clearFocus()
        }
    }
}

fun MaterialAutoCompleteTextView.update(list: List<String>, startIndex: Int?) {
    setAdapter(ArrayAdapter(
            context,
            R.layout.dropdown_menu_popup_item,
            list
    ))
    allElements = list
    setText(list.getOrNull(startIndex ?: -1)?.toString(), false)
}

@Suppress("UNCHECKED_CAST")
private var MaterialAutoCompleteTextView.allElements: List<Any>
    get() = (tag as List<Any>)
    set(value) {
        tag = value
    }

fun View.setClickableBackground() {
    TypedValue().also { outValue ->
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        setBackgroundResource(outValue.resourceId)
    }
    isClickable = true
    isFocusable = true
}

fun Fragment.showTimePicker(tag: String, startTime: Instant?, onDateSelected: (Instant) -> Unit) {
    val time = startTime ?: Instant.ofEpochMilli(0L)
    val picker = MaterialTimePicker.Builder()
            .setHour(time.atZone(ZoneOffset.UTC).hour)
            .setMinute(time.atZone(ZoneOffset.UTC).minute)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()
    picker.addOnPositiveButtonClickListener {
        onDateSelected(Instant.ofEpochMilli(((picker.hour * 60) + (picker.minute)) * 60L * 1000L))
    }
    picker.show(parentFragmentManager, tag)
}

fun Fragment.showDatePicker(tag: String, startDate: Instant?, onDateSelected: (Instant) -> Unit) {
    val picker = MaterialDatePicker.Builder.datePicker()
        .setSelection((startDate?: Instant.now()).toEpochMilli())
        .build()
    picker.addOnPositiveButtonClickListener {
        onDateSelected(Instant.ofEpochMilli(it))
    }
    picker.show(parentFragmentManager, tag)
}

fun Fragment.showDateTimePicker(tag: String, startDateTime: Instant?, onDateTimeSelected: (Instant) -> Unit) {
    showDatePicker("${tag}_date", startDateTime) { date ->
        showTimePicker("${tag}_time", startDateTime) { time ->
            val result = date.atZone(ZoneOffset.systemDefault())
                    .withHour(time.atZone(ZoneOffset.UTC).hour)
                    .withMinute(time.atZone(ZoneOffset.UTC).minute)
                    .withSecond(0)
                    .withNano(date.nano)
                    .toInstant()
            onDateTimeSelected(result)
        }
    }
}
