package pl.polsl.workflow.manager.client.util.extension

import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.mGetColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Context.hasPermission(key: String): Boolean {
    return ContextCompat.checkSelfPermission(this, key) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}


