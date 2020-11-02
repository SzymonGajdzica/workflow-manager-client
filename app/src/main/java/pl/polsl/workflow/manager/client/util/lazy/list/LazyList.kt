package pl.polsl.workflow.manager.client.util.lazy.list

import pl.polsl.workflow.manager.client.model.remote.data.IdentifiableApiModel

interface LazyList<T: IdentifiableApiModel> {

    suspend fun getItem(id: Long): T

    suspend fun getAll(): List<T>

    suspend fun safeGetItem(id: Long): T?

    fun supplyItem(item: T)

    fun removeItem(id: Long)

    fun clear()

}