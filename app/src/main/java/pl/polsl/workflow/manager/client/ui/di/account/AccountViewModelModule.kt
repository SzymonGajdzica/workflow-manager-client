package pl.polsl.workflow.manager.client.ui.di.account

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.worker.account.AccountViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class AccountViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModelImpl::class)
    abstract fun bindsViewModel(accountViewModelImpl: AccountViewModelImpl): ViewModel
}