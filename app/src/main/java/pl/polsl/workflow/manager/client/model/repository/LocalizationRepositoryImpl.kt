package pl.polsl.workflow.manager.client.model.repository

import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.model.data.LocalizationPost
import pl.polsl.workflow.manager.client.model.mapper.map
import pl.polsl.workflow.manager.client.model.remote.source.LocalizationDataSource
import pl.polsl.workflow.manager.client.model.safeCall

class LocalizationRepositoryImpl(
    private val localizationDataSource: LocalizationDataSource
) : LocalizationRepository {

    override suspend fun getAllLocalizations(): RepositoryResult<List<Localization>> = safeCall {
        localizationDataSource.getAllLocalizations().getAll().map { it.map() }
    }

    override suspend fun addLocalization(localizationPost: LocalizationPost): RepositoryResult<Localization> = safeCall {
        localizationDataSource.addLocalization(localizationPost.map()).map()
    }

}