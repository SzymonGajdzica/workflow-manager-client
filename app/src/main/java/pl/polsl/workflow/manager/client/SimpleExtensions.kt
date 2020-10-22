package pl.polsl.workflow.manager.client

import android.content.Context
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

fun Context.showToast(text: String): Toast {
    return Toast.makeText(this, text, Toast.LENGTH_SHORT).apply {
        show()
    }
}

fun TextInputLayout.disableErrorOnWrite() {
    editText?.doOnTextChanged { _, _, _, _ ->
        if(error != null)
            error = null
    }
}

fun Long.millisecondsToHoursMinutesSeconds(): String {
    val seconds = (this / 1000L) % 60L
    val minutes = (this / (1000L * 60L) % 60L)
    val hours = (this / (1000L * 60L * 60L) % 24L)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
