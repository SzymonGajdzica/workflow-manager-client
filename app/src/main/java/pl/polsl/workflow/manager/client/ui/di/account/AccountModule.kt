package pl.polsl.workflow.manager.client.ui.di.account

import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.GroupApi
import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.repository.GroupRepository
import pl.polsl.workflow.manager.client.model.remote.repository.GroupRepositoryImpl
import pl.polsl.workflow.manager.client.ui.di.module.NetworkModule
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [AccountViewModelModule::class, NetworkModule::class])
class AccountModule {

    @Provides
    @Singleton
    fun provideGroupApi(retrofit: Retrofit): GroupApi = retrofit.create()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()

    @Provides
    @Singleton
    fun provideGroupRepository(userApi: UserApi, groupApi: GroupApi): GroupRepository = GroupRepositoryImpl(userApi, groupApi)

}