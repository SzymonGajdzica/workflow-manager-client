package pl.polsl.workflow.manager.client.ui.di.task

import dagger.Component
import pl.polsl.workflow.manager.client.ui.worker.task.TaskFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [TaskModule::class])
interface TaskComponent {
    fun inject(taskFragment: TaskFragment)
}