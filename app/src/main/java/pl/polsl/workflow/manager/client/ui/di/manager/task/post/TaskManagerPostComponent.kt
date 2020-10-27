package pl.polsl.workflow.manager.client.ui.di.manager.task.post

import dagger.Component
import pl.polsl.workflow.manager.client.ui.manager.task.post.TaskManagerPostFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [TaskManagerPostModule::class])
interface TaskManagerPostComponent {
    fun inject(taskManagerPostFragment: TaskManagerPostFragment)
}