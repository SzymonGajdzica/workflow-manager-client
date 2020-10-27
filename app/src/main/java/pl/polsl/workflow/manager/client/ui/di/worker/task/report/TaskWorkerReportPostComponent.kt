package pl.polsl.workflow.manager.client.ui.di.worker.task.report

import dagger.Component
import pl.polsl.workflow.manager.client.ui.worker.task.report.TaskWorkerReportPostFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [TaskWorkerReportPostModule::class])
interface TaskWorkerReportPostComponent {
    fun inject(taskWorkerReportPostFragment: TaskWorkerReportPostFragment)
}