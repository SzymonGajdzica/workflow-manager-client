package pl.polsl.workflow.manager.client.ui.di.task.report

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.model.remote.api.TaskApi
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepository
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepositoryImpl
import pl.polsl.workflow.manager.client.ui.di.module.NetworkModule
import pl.polsl.workflow.manager.client.utils.LocationReader
import pl.polsl.workflow.manager.client.utils.LocationReaderImpl
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [TaskWorkerReportPostViewModelModule::class, NetworkModule::class])
class TaskWorkerReportPostModule {

    @Provides
    @Singleton
    fun provideLocalizationApi(retrofit: Retrofit): LocalizationApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTaskApi(retrofit: Retrofit): TaskApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTaskRepository(taskApi: TaskApi, localizationApi: LocalizationApi): TaskRepository = TaskRepositoryImpl(taskApi, localizationApi)

    @Provides
    @Singleton
    fun provideLocationReader(context: Context): LocationReader = LocationReaderImpl(context)

}