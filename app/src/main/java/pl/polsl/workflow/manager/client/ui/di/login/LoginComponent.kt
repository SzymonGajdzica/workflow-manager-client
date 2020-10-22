package pl.polsl.workflow.manager.client.ui.di.login

import dagger.Component
import pl.polsl.workflow.manager.client.ui.di.modules.AppModule
import pl.polsl.workflow.manager.client.ui.di.modules.NetworkModule
import pl.polsl.workflow.manager.client.ui.login.LoginFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, LoginModule::class])
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}