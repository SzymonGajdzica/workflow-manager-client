package pl.polsl.workflow.manager.client.util.extension

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf

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

inline fun <reified T: Parcelable> Bundle.getParcelable(): T? {
    return getParcelable<T>(T::class.java.simpleName)
}

inline fun <reified T: Parcelable> Bundle.getParcelableList(): List<T>? {
    return getParcelableArrayList("List<${T::class.java.simpleName}>")
}