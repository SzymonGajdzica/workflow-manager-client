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
        val items = items
        val quickList = when {
            items?.contains(id) == true -> items
            else -> loadData()
        }
        return quickList.getItem(id)
    }

    override suspend fun safeGetItem(id: Long): T? {
        val items = items ?: loadData()
        return if(items.contains(id)) items.getItem(id) else null
    }

    override suspend fun getAll(): List<T> {
        return items?.getAll() ?: loadData().getAll()
    }

    protected open suspend fun loadData(): QuickList<T> {
        loadTask?.let { return it.await() }
        clear()
        val loadTask = GlobalScope.async {
            QuickList(dateGetter()).also { items = it }
        }
        this.loadTask = loadTask
        return loadTask.await().also { this.loadTask = null }
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