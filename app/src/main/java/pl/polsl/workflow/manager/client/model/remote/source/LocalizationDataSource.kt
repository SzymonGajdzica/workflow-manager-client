package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.data.LocalizationApiModel
import pl.polsl.workflow.manager.client.model.remote.data.LocalizationApiModelPost
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList

interface LocalizationDataSource {

    fun getAllLocalizations(): LazyList<LocalizationApiModel>

    suspend fun addLocalization(localizationApiModelPost: LocalizationApiModelPost): LocalizationApiModel

}