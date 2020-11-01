package pl.polsl.workflow.manager.client.util.lazy.list

import pl.polsl.workflow.manager.client.model.data.toMap
import pl.polsl.workflow.manager.client.model.remote.data.IdentifiableApiModel
import java.util.*
import kotlin.collections.HashMap

class QuickList<T: IdentifiableApiModel>(
        list: List<T>
) {

    private val mList: LinkedList<T> = LinkedList(list)
    private val map: HashMap<Long, T> = HashMap(mList.toMap())

    fun contains(id: Long): Boolean {
        synchronized(this) {
            return map.containsKey(id)
        }
    }

    fun getItem(id: Long): T {
        synchronized(this) {
            return map.getValue(id)
        }
    }

    fun getAll(): List<T> {
        synchronized(this) {
            return mList
        }
    }

    fun addItem(item: T) {
        synchronized(this) {
            if(map.containsKey(item.id))
                mList.remove(item)
            mList.add(item)
            map[item.id] = item
        }
    }

    fun removeItem(id: Long) {
        synchronized(this) {
            map.remove(id)?.let {
                mList.remove(it)
            }
        }
    }

}