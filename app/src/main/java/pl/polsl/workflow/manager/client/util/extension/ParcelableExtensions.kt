package pl.polsl.workflow.manager.client.util.extension

import android.os.Bundle
import androidx.core.os.bundleOf

fun Any.toBundle(): Bundle {
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

inline fun <reified T> Bundle.get(): T? {
    return get(T::class.java.simpleName) as? T
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> Bundle.getList(): List<T>? {
    return get("List<${T::class.java.simpleName}>") as? List<T>
}