package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.api.UserApi
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModel
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModelPatch
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModelPost
import pl.polsl.workflow.manager.client.util.lazy.list.ExpirableCachedLazyList
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList
import java.time.Instant

class UserDataSourceImpl(
        private val userApi: UserApi
): UserDataSource {

    private val lazyList: LazyList<UserApiModel> = ExpirableCachedLazyList(Instant.ofEpochSecond(24L * 60L * 60L)) {
        userApi.getAllUsers()
    }

    override suspend fun getSelf(): UserApiModel {
        return userApi.getSelf()
    }

    override fun getAllUsers(): LazyList<UserApiModel> {
        return lazyList
    }

    override suspend fun createUser(userApiModelPost: UserApiModelPost): UserApiModel {
        return userApi.createUser(userApiModelPost).also {
            lazyList.supplyItem(it)
        }
    }

    override suspend fun updateUser(userId: Long, userApiModelPatch: UserApiModelPatch): UserApiModel {
        return userApi.updateUser(userId, userApiModelPatch).also {
            lazyList.supplyItem(it)
        }
    }

}