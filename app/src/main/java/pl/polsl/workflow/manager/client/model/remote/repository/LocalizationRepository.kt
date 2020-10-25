package pl.polsl.workflow.manager.client.model.remote.repository

import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.model.data.LocalizationPost
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult

interface LocalizationRepository {

    suspend fun getAllLocalizations(): RepositoryResult<List<Localization>>

    suspend fun addLocalization(localizationPost: LocalizationPost): RepositoryResult<Localization>

}