package pl.polsl.workflow.manager.client.ui.di.manager.task

import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.GroupApi
import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.model.remote.api.TaskApi
import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.repository.GroupRepository
import pl.polsl.workflow.manager.client.model.remote.repository.GroupRepositoryImpl
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepository
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepositoryImpl
import pl.polsl.workflow.manager.client.ui.di.module.NetworkModule
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [TaskManagerViewModelModule::class, NetworkModule::class])
class TaskManagerModule {

    @Provides
    @Singleton
    fun provideLocalizationApi(retrofit: Retrofit): LocalizationApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTaskApi(retrofit: Retrofit): TaskApi = retrofit.create()

    @Provides
    @Singleton
    fun provideGroupApi(retrofit: Retrofit): GroupApi = retrofit.create()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTaskRepository(taskApi: TaskApi, localizationApi: LocalizationApi): TaskRepository = TaskRepositoryImpl(taskApi, localizationApi)

    @Provides
    @Singleton
    fun provideGroupRepository(userApi: UserApi, groupApi: GroupApi): GroupRepository = GroupRepositoryImpl(userApi, groupApi)

}