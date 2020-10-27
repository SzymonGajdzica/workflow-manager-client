package pl.polsl.workflow.manager.client.ui.di.manager.account

import dagger.Component
import pl.polsl.workflow.manager.client.ui.manager.account.AccountManagerFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AccountManagerModule::class])
interface AccountManagerComponent {
    fun inject(accountManagerFragment: AccountManagerFragment)
}