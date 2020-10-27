package pl.polsl.workflow.manager.client.ui.di.map

import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.model.remote.repository.LocalizationRepository
import pl.polsl.workflow.manager.client.model.remote.repository.LocalizationRepositoryImpl
import pl.polsl.workflow.manager.client.ui.di.module.NetworkModule
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [MapSelectViewModelModule::class, NetworkModule::class])
class MapSelectModule {

    @Provides
    @Singleton
    fun provideLocalizationApi(retrofit: Retrofit): LocalizationApi = retrofit.create()

    @Provides
    @Singleton
    fun provideGroupRepository(localizationApi: LocalizationApi): LocalizationRepository = LocalizationRepositoryImpl(localizationApi)

}