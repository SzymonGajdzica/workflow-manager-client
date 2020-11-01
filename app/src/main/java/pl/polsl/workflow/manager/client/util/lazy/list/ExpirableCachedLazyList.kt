package pl.polsl.workflow.manager.client.util.lazy.list

import pl.polsl.workflow.manager.client.model.remote.data.IdentifiableApiModel
import java.time.Instant

class ExpirableCachedLazyList<T: IdentifiableApiModel>(
        private val expireTime: Instant,
        dateGetter: suspend () -> List<T>
): CachedLazyList<T>(dateGetter) {

    private var expireDate = Instant.now()
    private var mItems: QuickList<T>? = null

    override var items: QuickList<T>?
        get() = when {
            mItems == null -> null
            expireDate > Instant.now() -> mItems
            else -> null
        }
        set(value) {
            if(value != null)
                expireDate = expireTime.plusMillis(System.currentTimeMillis())
            mItems = value
        }

}