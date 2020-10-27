package pl.polsl.workflow.manager.client.ui.di.manager.account

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.manager.account.AccountManagerViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class AccountManagerViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountManagerViewModelImpl::class)
    abstract fun bindsViewModel(accountManagerViewModelImpl: AccountManagerViewModelImpl): ViewModel
}