package pl.polsl.workflow.manager.client.ui.di.login

import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.AuthenticationApi
import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.repository.AuthenticationRepository
import pl.polsl.workflow.manager.client.model.remote.repository.AuthenticationRepositoryImpl
import pl.polsl.workflow.manager.client.model.remote.repository.UserRepository
import pl.polsl.workflow.manager.client.model.remote.repository.UserRepositoryImpl
import pl.polsl.workflow.manager.client.ui.di.module.NetworkModule
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [LoginViewModelModule::class, NetworkModule::class])
class LoginModule {

    @Provides
    @Singleton
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi = retrofit.create()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()

    @Provides
    @Singleton
    fun provideAuthenticationRepository(authenticationApi: AuthenticationApi): AuthenticationRepository = AuthenticationRepositoryImpl(authenticationApi)

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository = UserRepositoryImpl(userApi)



}