package pl.polsl.workflow.manager.client.ui.di.login

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.login.LoginViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class LoginViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModelImpl::class)
    abstract fun bindsViewModel(loginViewModelImpl: LoginViewModelImpl): ViewModel
}