package pl.polsl.workflow.manager.client.util.lazy.list

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pl.polsl.workflow.manager.client.model.remote.data.IdentifiableApiModel

open class CachedLazyList<T: IdentifiableApiModel>(
        private val dateGetter: suspend () -> List<T>
): LazyList<T> {

    open var items: QuickList<T>? = null

    private var loadTask: Deferred<QuickList<T>>? = null

    override suspend fun getItem(id: Long): T {
        return getQuickList(id).getItem(id)
    }

    protected open suspend fun getQuickList(id: Long): QuickList<T> {
        val items = items
        return when {
            items?.contains(id) == true -> items
            else -> loadData()
        }
    }

    override suspend fun contains(id: Long): Boolean {
        return getQuickList(id).contains(id)
    }

    override suspend fun getAll(): List<T> {
        return items?.getAll() ?: loadData().getAll()
    }

    protected open suspend fun loadData(): QuickList<T> {
        loadTask?.let {
            return it.await()
        }
        clear()
        val loadTask = GlobalScope.async {
            QuickList(dateGetter()).also {
                items = it
            }
        }
        this.loadTask = loadTask
        return loadTask.await().also {
            this.loadTask = null
        }
    }

    override fun supplyItem(item: T) {
        items?.addItem(item)
    }

    override fun removeItem(id: Long) {
        items?.removeItem(id)
    }

    override fun clear() {
        items = null
    }

}