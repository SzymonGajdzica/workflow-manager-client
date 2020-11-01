package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.model.remote.data.LocalizationApiModel
import pl.polsl.workflow.manager.client.model.remote.data.LocalizationApiModelPost
import pl.polsl.workflow.manager.client.util.lazy.list.ExpirableCachedLazyList
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList
import java.time.Instant

class LocalizationDataSourceImpl(
        private val localizationApi: LocalizationApi
): LocalizationDataSource {

    private val lazyList: LazyList<LocalizationApiModel> = ExpirableCachedLazyList(Instant.ofEpochSecond(30L * 60L)) {
        localizationApi.getAllLocalizations()
    }

    override fun getAllLocalizations(): LazyList<LocalizationApiModel> {
        return lazyList
    }

    override suspend fun addLocalization(localizationApiModelPost: LocalizationApiModelPost): LocalizationApiModel {
        return localizationApi.addLocalization(localizationApiModelPost).also {
            lazyList.supplyItem(it)
        }
    }
}