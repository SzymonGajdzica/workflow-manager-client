package pl.polsl.workflow.manager.client

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import pl.polsl.workflow.manager.client.model.data.LatLng
import pl.polsl.workflow.manager.client.ui.view.SimpleAdapter
import java.time.Instant
import kotlin.math.abs

fun RecyclerView.setupSimpleAdapterSingle(
        @LayoutRes viewId: Int = R.layout.base_list_item,
        list: List<String>,
        onClick: (Int) -> Unit = {}
) {
    setupSimpleAdapter(viewId, list.map { listOf(it) }, onClick)
}

fun RecyclerView.setupSimpleAdapter(
        @LayoutRes viewId: Int = R.layout.base_list_item,
        list: List<List<String>>,
        onClick: (Int) -> Unit = {}
) {
    val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context)
    addItemDecoration(dividerItemDecoration)
    adapter = SimpleAdapter(viewId, list, onClick)
}

fun Context.showToast(@StringRes resId: Int): Toast {
    return showToast(getString(resId))
}

fun Context.showToast(text: String): Toast {
    return Toast.makeText(this, text, Toast.LENGTH_SHORT).apply {
        show()
    }
}

fun Context.mGetColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun TextInputLayout.disableErrorOnWrite() {
    editText?.doOnTextChanged { _, _, _, _ ->
        if(error != null)
            error = null
    }
}

fun Instant.toHoursMinutesSeconds(): String {
    val prefix = if(toEpochMilli() < 0L) "-" else ""
    val millis = abs(toEpochMilli())
    val seconds = (millis / 1000L) % 60L
    val minutes = (millis / (1000L * 60L) % 60L)
    val hours = (millis / (1000L * 60L * 60L) % 24L)
    return String.format("$prefix%02d:%02d:%02d", hours, minutes, seconds)
}

fun Context.hasPermission(key: String): Boolean {
    return ContextCompat.checkSelfPermission(this, key) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

fun LatLng.toGoogleLatLng(): com.google.android.gms.maps.model.LatLng {
    return com.google.android.gms.maps.model.LatLng(latitude, longitude)
}

