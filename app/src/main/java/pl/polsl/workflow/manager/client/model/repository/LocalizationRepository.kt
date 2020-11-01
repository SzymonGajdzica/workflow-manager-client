package pl.polsl.workflow.manager.client.model.repository

import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.model.data.LocalizationPost

interface LocalizationRepository {

    suspend fun getAllLocalizations(): RepositoryResult<List<Localization>>

    suspend fun addLocalization(localizationPost: LocalizationPost): RepositoryResult<Localization>

}