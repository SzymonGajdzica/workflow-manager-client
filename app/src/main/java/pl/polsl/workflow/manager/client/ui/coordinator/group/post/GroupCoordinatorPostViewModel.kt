package pl.polsl.workflow.manager.client.ui.coordinator.group.post

import android.app.Application
import androidx.lifecycle.LiveData
import pl.polsl.workflow.manager.client.model.data.GroupPost
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.ui.base.BaseViewModel

abstract class GroupCoordinatorPostViewModel(application: Application): BaseViewModel(application) {

    abstract val nameInputError: LiveData<String>
    abstract val managers: LiveData<List<User>>

    abstract fun createGroup(groupPost: GroupPost)

}