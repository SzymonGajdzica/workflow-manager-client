package pl.polsl.workflow.manager.client.ui.di.account

import dagger.Component
import pl.polsl.workflow.manager.client.ui.worker.account.AccountFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AccountModule::class])
interface AccountComponent {
    fun inject(accountFragment: AccountFragment)
}