package pl.polsl.workflow.manager.client

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData

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

fun Context.hasPermission(key: String): Boolean {
    return ContextCompat.checkSelfPermission(this, key) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

val <T>LiveData<T>.safeValue: T
    get() = value ?: throw NullPointerException()

fun Parcelable.toBundle(): Bundle {
    return bundleOf(Pair(javaClass.simpleName, this))
}

inline fun <reified T: Parcelable>Bundle.getParcelable(): T? {
    return getParcelable<T>(T::class.java.simpleName)
}

