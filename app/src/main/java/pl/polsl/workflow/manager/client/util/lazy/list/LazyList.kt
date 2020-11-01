package pl.polsl.workflow.manager.client.util.lazy.list

interface LazyList<T> {

    suspend fun getItem(id: Long): T

    suspend fun contains(id: Long): Boolean

    suspend fun getAll(): List<T>

    fun supplyItem(item: T)

    fun removeItem(id: Long)

    fun clear()

}