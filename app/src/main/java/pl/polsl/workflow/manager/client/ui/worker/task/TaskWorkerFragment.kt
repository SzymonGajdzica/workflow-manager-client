package pl.polsl.workflow.manager.client.ui.worker.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentTaskWorkerBinding
import pl.polsl.workflow.manager.client.model.data.TaskStatus
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.ui.view.SimpleAdapter
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.setupSimpleAdapter
import pl.polsl.workflow.manager.client.ui.view.update
import pl.polsl.workflow.manager.client.util.extension.safeValue
import pl.polsl.workflow.manager.client.util.extension.toBundle
import pl.polsl.workflow.manager.client.util.extension.toHoursMinutesSeconds

class TaskWorkerFragment : BaseFragmentViewModel<TaskWorkerViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskWorkerBinding

    override val viewModel: TaskWorkerViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentTaskWorkerBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.appComponent.inject(this)
    }

    override fun setupViews() {
        super.setupViews()
        viewDataBinding.workerTaskTaskStatusDropdown.update(
            resources.getStringArray(R.array.workerTaskStatuses).toList(),
            taskStatusToPosition(viewModel.selectedTaskStatus.safeValue)
        )
        viewDataBinding.workerTaskTaskList.setupSimpleAdapter {
            val task = viewModel.tasks.safeValue[it]
            findNavController().navigate(
                    R.id.action_navigation_task_worker_to_task_details_navigation,
                    listOf(task, viewModel.getSharedTasks(task)).toBundle()
            )
        }
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.remainingTime.observe {
            viewDataBinding.workerTaskRemainingTime.text =
                ("${getString(R.string.timeToCompleteTask)}: ${it?.toHoursMinutesSeconds() ?: "-"}")
        }
        viewModel.tasks.observe { tasks ->
            val list = tasks?.map { it.name } ?: listOf()
            (viewDataBinding.workerTaskTaskList.adapter as? SimpleAdapter)?.updateSingleList(list)
        }
    }

    override fun setupOnLayoutInteractions() {
        super.setupOnLayoutInteractions()
        viewDataBinding.workerTaskFailure.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_task_to_navigation_task_worker_report_post,
                    viewModel.task.value?.toBundle()
            )
        }
        viewDataBinding.workerTaskSuccess.setOnClickListener {
            viewModel.startTask()
        }
        viewDataBinding.workerTaskLocalization.setOnClickListener {
            findNavController().navigate(
                    R.id.action_navigation_home_to_mapsFragment,
                    viewModel.task.value?.localization?.toBundle()
            )
        }
        viewDataBinding.workerTaskTaskStatusDropdown.mSetOnItemSelectedListener { position ->
            viewModel.taskStatusSelected(positionToTaskStatus(position))
        }
    }

    private fun positionToTaskStatus(position: Int): Int {
        return when(position) {
            0 -> TaskStatus.FINISHED
            else -> TaskStatus.ACCEPTED
        }
    }

    private fun taskStatusToPosition(taskStatus: Int): Int {
        return when(taskStatus) {
            TaskStatus.FINISHED -> 0
            else -> 1
        }
    }

}