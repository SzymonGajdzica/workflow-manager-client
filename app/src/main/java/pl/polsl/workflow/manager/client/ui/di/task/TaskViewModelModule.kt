package pl.polsl.workflow.manager.client.ui.di.task

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.worker.task.TaskViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class TaskViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModelImpl::class)
    abstract fun bindsViewModel(taskViewModelImpl: TaskViewModelImpl): ViewModel
}