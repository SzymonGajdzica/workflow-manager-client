package pl.polsl.workflow.manager.client.ui.di.module

import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.*
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [NetworkModule::class])
class ApiModule {

    @Provides
    fun provideLocalizationApi(retrofit: Retrofit): LocalizationApi = retrofit.create()

    @Provides
    fun provideTaskApi(retrofit: Retrofit): TaskApi = retrofit.create()

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()

    @Provides
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi = retrofit.create()

    @Provides
    fun provideGroupApi(retrofit: Retrofit): GroupApi = retrofit.create()

}