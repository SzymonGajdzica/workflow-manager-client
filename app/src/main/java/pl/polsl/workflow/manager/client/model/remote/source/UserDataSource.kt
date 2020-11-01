package pl.polsl.workflow.manager.client.model.remote.source

import pl.polsl.workflow.manager.client.model.remote.data.UserApiModel
import pl.polsl.workflow.manager.client.model.remote.data.UserApiModelPost
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList

interface UserDataSource {

    suspend fun getSelf(): UserApiModel

    fun getAllUsers(): LazyList<UserApiModel>

    suspend fun createUser(userApiModelPost: UserApiModelPost): UserApiModel

}