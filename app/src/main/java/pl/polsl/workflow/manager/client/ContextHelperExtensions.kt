package pl.polsl.workflow.manager.client

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData

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

fun Collection<Any?>.toBundle(): Bundle {
    return bundleOf(*(map {
        val name = if (it is Collection<*>) {
            "List<${it.firstOrNull()?.javaClass?.simpleName?.toString()}>"
        } else
            it?.javaClass?.simpleName.toString()
        Pair(name, it)
    }.toTypedArray()))
}

inline fun <reified T: Parcelable>Bundle.getParcelable(): T? {
    return getParcelable<T>(T::class.java.simpleName)
}

inline fun <reified T: Parcelable>Bundle.getParcelableList(): List<T>? {
    return getParcelableArrayList("List<${T::class.java.simpleName}>")
}

