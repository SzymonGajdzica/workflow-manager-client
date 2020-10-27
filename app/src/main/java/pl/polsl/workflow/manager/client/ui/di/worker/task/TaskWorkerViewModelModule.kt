package pl.polsl.workflow.manager.client.ui.di.worker.task

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.worker.task.TaskWorkerViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class TaskWorkerViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TaskWorkerViewModelImpl::class)
    abstract fun bindsViewModel(taskWorkerViewModelImpl: TaskWorkerViewModelImpl): ViewModel
}