package pl.polsl.workflow.manager.client.ui.di.module

import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.source.*
import pl.polsl.workflow.manager.client.model.repository.*

@Module(includes = [DataSourceModule::class])
class RepositoryModule {

    @Provides
    fun provideLocalizationRepository(localizationDataSource: LocalizationDataSource): LocalizationRepository = LocalizationRepositoryImpl(localizationDataSource)

    @Provides
    fun provideGroupRepository(userDataSource: UserDataSource, groupDataSource: GroupDataSource): GroupRepository = GroupRepositoryImpl(userDataSource, groupDataSource)

    @Provides
    fun provideTaskRepository(taskDataSource: TaskDataSource, localizationDataSource: LocalizationDataSource, userDataSource: UserDataSource, groupDataSource: GroupDataSource): TaskRepository = TaskRepositoryImpl(taskDataSource, localizationDataSource, userDataSource, groupDataSource)

    @Provides
    fun provideAuthenticationRepository(authenticationDataSource: AuthenticationDataSource): AuthenticationRepository = AuthenticationRepositoryImpl(authenticationDataSource)

    @Provides
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository = UserRepositoryImpl(userDataSource)

}