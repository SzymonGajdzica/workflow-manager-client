package pl.polsl.workflow.manager.client.ui.di.task.report

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.worker.task.report.TaskWorkerReportPostViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class TaskWorkerReportPostViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TaskWorkerReportPostViewModelImpl::class)
    abstract fun bindsViewModel(viewModelImpl: TaskWorkerReportPostViewModelImpl): ViewModel
}