package pl.polsl.workflow.manager.client.ui.view

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.polsl.workflow.manager.client.R
import java.time.Instant
import java.time.ZoneOffset

fun RecyclerView.setupSimpleAdapter(
        @LayoutRes viewId: Int = R.layout.base_list_item,
        onClick: ((Int) -> Unit)? = null
) {
    setupAdapter(SimpleAdapter(viewId, onClick))
}

fun RecyclerView.setupAdapter(mAdapter: RecyclerView.Adapter<*>) {
    val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context)
    addItemDecoration(dividerItemDecoration)
    adapter = mAdapter
}

fun Spinner.mSetOnItemSelectedListener(callback: (Int) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            callback(position)
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }
}

fun MaterialAutoCompleteTextView.mSetOnItemSelectedListener(callback: (Int) -> Unit) {
    onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
        callback(position)
    }
    setOnDismissListener {
        clearFocus()
    }
}

fun MaterialAutoCompleteTextView.update(list: List<String>, startIndex: Int?) {
    CoroutineScope(Dispatchers.Main).launch {
        setAdapter(ArrayAdapter(
            context,
            R.layout.dropdown_menu_popup_item,
            list
        ))
        setText(list.getOrNull(startIndex ?: 0)?.toString(), false)
    }
}

fun Spinner.setupSimpleArrayAdapter(context: Context) {
    adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}

@Suppress("UNCHECKED_CAST")
val Spinner.arrayAdapter: ArrayAdapter<String>?
    get() = adapter as? ArrayAdapter<String>

fun ArrayAdapter<String>.update(collection: Collection<String>) {
    clear()
    addAll(collection)
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
