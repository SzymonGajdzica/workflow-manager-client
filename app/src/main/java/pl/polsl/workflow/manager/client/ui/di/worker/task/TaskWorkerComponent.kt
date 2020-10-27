package pl.polsl.workflow.manager.client.ui.di.worker.task

import dagger.Component
import pl.polsl.workflow.manager.client.ui.worker.task.TaskWorkerFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [TaskWorkerModule::class])
interface TaskWorkerComponent {
    fun inject(taskWorkerFragment: TaskWorkerFragment)
}