package pl.polsl.workflow.manager.client.ui.di.worker.account

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.worker.account.AccountWorkerViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class AccountWorkerViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountWorkerViewModelImpl::class)
    abstract fun bindsViewModel(accountViewModelImpl: AccountWorkerViewModelImpl): ViewModel
}