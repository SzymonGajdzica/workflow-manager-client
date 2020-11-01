package pl.polsl.workflow.manager.client.util.extension

import androidx.lifecycle.LiveData

val <T>LiveData<T>.safeValue: T
    get() = value ?: throw NullPointerException()