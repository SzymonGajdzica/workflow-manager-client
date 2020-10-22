package pl.polsl.workflow.manager.client.ui.di.login

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.AuthenticationApi
import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.repository.AuthenticationRepository
import pl.polsl.workflow.manager.client.model.remote.repository.AuthenticationRepositoryImpl
import pl.polsl.workflow.manager.client.model.remote.repository.UserRepository
import pl.polsl.workflow.manager.client.model.remote.repository.UserRepositoryImpl
import pl.polsl.workflow.manager.client.utils.TokenHolder
import pl.polsl.workflow.manager.client.utils.TokenHolderImpl
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [LoginViewModelModule::class])
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

    @Provides
    @Singleton
    fun provideTokenHolder(sharedPreferences: SharedPreferences): TokenHolder = TokenHolderImpl(sharedPreferences)

}