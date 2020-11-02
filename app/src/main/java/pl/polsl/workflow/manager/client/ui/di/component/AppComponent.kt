package pl.polsl.workflow.manager.client.ui.di.component

import dagger.Component
import pl.polsl.workflow.manager.client.ui.coordinator.account.AccountCoordinatorFragment
import pl.polsl.workflow.manager.client.ui.coordinator.account.post.AccountCoordinatorPostFragment
import pl.polsl.workflow.manager.client.ui.coordinator.group.GroupCoordinatorFragment
import pl.polsl.workflow.manager.client.ui.coordinator.localization.LocalizationCoordinatorFragment
import pl.polsl.workflow.manager.client.ui.coordinator.localization.post.LocalizationCoordinatorPostFragment
import pl.polsl.workflow.manager.client.ui.di.module.LocalizationModule
import pl.polsl.workflow.manager.client.ui.di.module.RepositoryModule
import pl.polsl.workflow.manager.client.ui.di.module.ValidatorModule
import pl.polsl.workflow.manager.client.ui.di.module.ViewModelModules
import pl.polsl.workflow.manager.client.ui.login.LoginFragment
import pl.polsl.workflow.manager.client.ui.manager.account.AccountManagerFragment
import pl.polsl.workflow.manager.client.ui.manager.task.TaskManagerFragment
import pl.polsl.workflow.manager.client.ui.manager.task.post.TaskManagerPostFragment
import pl.polsl.workflow.manager.client.ui.manager.task.report.post.TaskManagerReportPostFragment
import pl.polsl.workflow.manager.client.ui.map.MapSelectFragment
import pl.polsl.workflow.manager.client.ui.worker.account.AccountWorkerFragment
import pl.polsl.workflow.manager.client.ui.worker.task.TaskWorkerFragment
import pl.polsl.workflow.manager.client.ui.worker.task.report.post.TaskWorkerReportPostFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModules::class, LocalizationModule::class, RepositoryModule::class, ValidatorModule::class])
interface AppComponent {
    fun inject(fragment: AccountWorkerFragment)
    fun inject(fragment: TaskManagerReportPostFragment)
    fun inject(fragment: TaskManagerFragment)
    fun inject(fragment: MapSelectFragment)
    fun inject(fragment: TaskWorkerFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: TaskManagerPostFragment)
    fun inject(fragment: TaskWorkerReportPostFragment)
    fun inject(fragment: AccountManagerFragment)
    fun inject(fragment: AccountCoordinatorFragment)
    fun inject(fragment: GroupCoordinatorFragment)
    fun inject(fragment: LocalizationCoordinatorFragment)
    fun inject(fragment: AccountCoordinatorPostFragment)
    fun inject(fragment: LocalizationCoordinatorPostFragment)
}