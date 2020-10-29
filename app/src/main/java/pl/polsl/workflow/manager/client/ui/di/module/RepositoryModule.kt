package pl.polsl.workflow.manager.client.ui.di.module

import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.*
import pl.polsl.workflow.manager.client.model.remote.repository.*
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalizationApi(retrofit: Retrofit): LocalizationApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTaskApi(retrofit: Retrofit): TaskApi = retrofit.create()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()

    @Provides
    @Singleton
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi = retrofit.create()

    @Provides
    @Singleton
    fun provideGroupApi(retrofit: Retrofit): GroupApi = retrofit.create()

    @Provides
    @Singleton
    fun provideLocalizationRepository(localizationApi: LocalizationApi): LocalizationRepository = LocalizationRepositoryImpl(localizationApi)

    @Provides
    @Singleton
    fun provideGroupRepository(userApi: UserApi, groupApi: GroupApi): GroupRepository = GroupRepositoryImpl(userApi, groupApi)

    @Provides
    @Singleton
    fun provideTaskRepository(taskApi: TaskApi, localizationApi: LocalizationApi, userApi: UserApi): TaskRepository = TaskRepositoryImpl(taskApi, localizationApi, userApi)

    @Provides
    @Singleton
    fun provideAuthenticationRepository(authenticationApi: AuthenticationApi): AuthenticationRepository = AuthenticationRepositoryImpl(authenticationApi)

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository = UserRepositoryImpl(userApi)

}