package pl.polsl.workflow.manager.client.ui.di.module

import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.*
import pl.polsl.workflow.manager.client.model.remote.source.*
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class DataSourceModule {

    @Provides
    @Singleton
    fun provideLocalizationDataSource(localizationApi: LocalizationApi): LocalizationDataSource = LocalizationDataSourceImpl(localizationApi)

    @Provides
    @Singleton
    fun provideTaskDataSource(taskApi: TaskApi): TaskDataSource = TaskDataSourceImpl(taskApi)

    @Provides
    @Singleton
    fun provideUserDataSource(userApi: UserApi): UserDataSource = UserDataSourceImpl(userApi)

    @Provides
    @Singleton
    fun provideAuthenticationDataSource(authenticationApi: AuthenticationApi): AuthenticationDataSource = AuthenticationDataSourceImpl(authenticationApi)

    @Provides
    @Singleton
    fun provideGroupDataSource(groupApi: GroupApi): GroupDataSource = GroupDataSourceImpl(groupApi)


}