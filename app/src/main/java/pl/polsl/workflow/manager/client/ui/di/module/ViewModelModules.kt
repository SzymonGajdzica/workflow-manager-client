package pl.polsl.workflow.manager.client.ui.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.login.LoginViewModelImpl
import pl.polsl.workflow.manager.client.ui.manager.account.AccountManagerViewModelImpl
import pl.polsl.workflow.manager.client.ui.manager.task.TaskManagerViewModelImpl
import pl.polsl.workflow.manager.client.ui.manager.task.post.TaskManagerPostViewModelImpl
import pl.polsl.workflow.manager.client.ui.manager.task.report.post.TaskManagerReportPostViewModelImpl
import pl.polsl.workflow.manager.client.ui.map.MapSelectViewModelImpl
import pl.polsl.workflow.manager.client.ui.worker.account.AccountWorkerViewModelImpl
import pl.polsl.workflow.manager.client.ui.worker.task.TaskWorkerViewModelImpl
import pl.polsl.workflow.manager.client.ui.worker.task.report.post.TaskWorkerReportPostViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class ViewModelModules {

    @Binds
    @IntoMap
    @ViewModelKey(TaskWorkerReportPostViewModelImpl::class)
    abstract fun bindsTaskWorkerReportPostViewModel(taskWorkerReportPostViewModelImpl: TaskWorkerReportPostViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModelImpl::class)
    abstract fun bindsLoginViewModel(loginViewModelImpl: LoginViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountManagerViewModelImpl::class)
    abstract fun bindsAccountManagerViewModel(accountManagerViewModelImpl: AccountManagerViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TaskManagerPostViewModelImpl::class)
    abstract fun bindsTaskManagerPostViewModel(taskManagerPostViewModelImpl: TaskManagerPostViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TaskManagerReportPostViewModelImpl::class)
    abstract fun bindsTaskManagerReportPostViewModel(viewModelImpl: TaskManagerReportPostViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TaskManagerViewModelImpl::class)
    abstract fun bindsTaskManagerViewModel(taskManagerViewModelImpl: TaskManagerViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapSelectViewModelImpl::class)
    abstract fun bindsMapSelectViewModel(mapSelectViewModelImpl: MapSelectViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountWorkerViewModelImpl::class)
    abstract fun bindsAccountWorkerViewModel(accountViewModelImpl: AccountWorkerViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TaskWorkerViewModelImpl::class)
    abstract fun bindsTaskWorkerViewModel(taskWorkerViewModelImpl: TaskWorkerViewModelImpl): ViewModel

}