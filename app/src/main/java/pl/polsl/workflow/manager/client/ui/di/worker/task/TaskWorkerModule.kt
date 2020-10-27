package pl.polsl.workflow.manager.client.ui.di.worker.task

import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.model.remote.api.TaskApi
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepository
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepositoryImpl
import pl.polsl.workflow.manager.client.ui.di.module.NetworkModule
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [TaskWorkerViewModelModule::class, NetworkModule::class])
class TaskWorkerModule {

    @Provides
    @Singleton
    fun provideLocalizationApi(retrofit: Retrofit): LocalizationApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTaskApi(retrofit: Retrofit): TaskApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTaskRepository(taskApi: TaskApi, localizationApi: LocalizationApi): TaskRepository = TaskRepositoryImpl(taskApi, localizationApi)

}