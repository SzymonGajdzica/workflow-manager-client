package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.model.data.LocalizationPost
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.model.remote.mapper.map
import pl.polsl.workflow.manager.client.model.remote.safeCall

class LocalizationRepositoryImpl(
    private val localizationApi: LocalizationApi
) : LocalizationRepository {

    override suspend fun getAllLocalizations(): RepositoryResult<List<Localization>> = safeCall {
        localizationApi.getAllLocalizations().map { it.map() }
    }

    override suspend fun addLocalization(localizationPost: LocalizationPost): RepositoryResult<Localization> = safeCall {
        localizationApi.addLocalization(localizationPost.map()).map()
    }

}