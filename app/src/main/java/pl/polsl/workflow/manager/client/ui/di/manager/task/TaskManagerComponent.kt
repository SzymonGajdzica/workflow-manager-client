package pl.polsl.workflow.manager.client.ui.di.manager.task

import dagger.Component
import pl.polsl.workflow.manager.client.ui.manager.task.TaskManagerFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [TaskManagerModule::class])
interface TaskManagerComponent {
    fun inject(taskManagerFragment: TaskManagerFragment)
}