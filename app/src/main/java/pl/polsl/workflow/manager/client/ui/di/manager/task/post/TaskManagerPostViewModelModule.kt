package pl.polsl.workflow.manager.client.ui.di.manager.task.post

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.manager.task.post.TaskManagerPostViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class TaskManagerPostViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TaskManagerPostViewModelImpl::class)
    abstract fun bindsViewModel(taskManagerPostViewModelImpl: TaskManagerPostViewModelImpl): ViewModel
}