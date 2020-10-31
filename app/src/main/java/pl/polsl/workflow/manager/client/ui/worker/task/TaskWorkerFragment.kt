package pl.polsl.workflow.manager.client.ui.worker.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_task_worker.view.*
import pl.polsl.workflow.manager.client.*
import pl.polsl.workflow.manager.client.databinding.FragmentTaskWorkerBinding
import pl.polsl.workflow.manager.client.model.data.TaskStatus
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.view.*

class TaskWorkerFragment : BaseFragment<TaskWorkerViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskWorkerBinding

    override val viewModel: TaskWorkerViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun setupViews(view: View) {
        super.setupViews(view)
        view.workerTaskTaskStatusDropdown.setupSimpleArrayAdapter(view.context)
        view.workerTaskTaskStatusDropdown.arrayAdapter?.update(view.context.resources.getStringArray(R.array.workerTaskStatuses).toList())
        view.workerTaskTaskList.setupSimpleAdapter {
            val task = viewModel.tasks.safeValue[it]
            findNavController().navigate(
                    R.id.action_navigation_task_worker_to_task_details_navigation,
                    listOf(task, viewModel.getSharedTasks(task)).toBundle()
            )
        }
    }

    override fun setupObservables(viewModel: TaskWorkerViewModel) {
        super.setupObservables(viewModel)
        viewModel.remainingTime.observe {
            view?.workerTaskRemainingTime?.text =
                ("${getString(R.string.timeToCompleteTask)}: ${it?.toHoursMinutesSeconds() ?: "-"}")
        }
        viewModel.tasks.observe { tasks ->
            val list = tasks?.map { it.name } ?: listOf()
            (view?.workerTaskTaskList?.adapter as? SimpleAdapter)?.updateSingleList(list)
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.workerTaskFailure.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_task_to_navigation_task_worker_report_post,
                    viewModel.task.value?.toBundle()
            )
        }
        view.workerTaskSuccess.setOnClickListener {
            viewModel.startTask()
        }
        view.workerTaskLocalization.setOnClickListener {
            findNavController().navigate(
                    R.id.action_navigation_home_to_mapsFragment,
                    viewModel.task.value?.localization?.toBundle()
            )
        }
        view.workerTaskTaskStatusDropdown.mSetOnItemSelectedListener {
            val taskStatus = when(it) {
                0 -> TaskStatus.FINISHED
                else -> TaskStatus.ACCEPTED
            }
            viewModel.taskStatusSelected(taskStatus)
        }
    }

}