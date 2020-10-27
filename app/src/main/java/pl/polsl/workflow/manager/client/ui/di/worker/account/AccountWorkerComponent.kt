package pl.polsl.workflow.manager.client.ui.di.worker.account

import dagger.Component
import pl.polsl.workflow.manager.client.ui.worker.account.AccountWorkerFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AccountWorkerModule::class])
interface AccountWorkerComponent {
    fun inject(accountFragment: AccountWorkerFragment)
}