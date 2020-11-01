package pl.polsl.workflow.manager.client

import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Test
import pl.polsl.workflow.manager.client.model.remote.data.IdentifiableApiModel
import pl.polsl.workflow.manager.client.util.lazy.list.CachedLazyList
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LazyListTest {

    data class Data(
            override val id: Long,
            val text: String
    ): IdentifiableApiModel

    @Test
    fun addition_isCorrect() {
        var dataCalls = 0
        val getter: suspend () -> List<Data> = {
            withContext(Dispatchers.IO) {
                dataCalls++
                delay(1000L)
                listOf(Data(1, "test1"), Data(2, "test2"), Data(3, "test3"))
            }
        }
        val lazyList: LazyList<Data> = CachedLazyList { getter() }
        CoroutineScope(Dispatchers.Default).launch {
            val jobs = arrayListOf<Job>()
            repeat(100) {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    if(it <= 50)
                        lazyList.getItem(1)
                    else
                        lazyList.getItem(4)
                }
                jobs.add(job)
                delay(10L)
                if(it == 50)
                    lazyList.supplyItem(Data(4, "test4"))
            }
            jobs.joinAll()
            assertEquals(1, dataCalls)
        }

    }

}