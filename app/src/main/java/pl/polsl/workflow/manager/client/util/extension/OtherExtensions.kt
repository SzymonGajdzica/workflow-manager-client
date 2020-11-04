package pl.polsl.workflow.manager.client.util.extension

import androidx.lifecycle.LiveData

val <T>LiveData<T>.safeValue: T
    get() = value ?: throw NullPointerException()

fun <T: Any>List<T>.indexOfOrNull(element: T?): Int? {
    return indexOf(element).let { if(it >= 0) it else null }
}