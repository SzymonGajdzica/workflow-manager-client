package pl.polsl.workflow.manager.client.ui.di.login

import dagger.Component
import pl.polsl.workflow.manager.client.ui.login.LoginFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}