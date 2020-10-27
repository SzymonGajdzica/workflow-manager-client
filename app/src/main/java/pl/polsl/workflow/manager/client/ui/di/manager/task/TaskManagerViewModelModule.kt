package pl.polsl.workflow.manager.client.ui.di.manager.task

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.manager.task.TaskManagerViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class TaskManagerViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TaskManagerViewModelImpl::class)
    abstract fun bindsViewModel(taskManagerViewModelImpl: TaskManagerViewModelImpl): ViewModel
}