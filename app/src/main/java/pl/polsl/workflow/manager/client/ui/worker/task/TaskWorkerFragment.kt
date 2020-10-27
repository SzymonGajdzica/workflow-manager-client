package pl.polsl.workflow.manager.client.ui.worker.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_task_worker.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentTaskWorkerBinding
import pl.polsl.workflow.manager.client.toHoursMinutesSeconds
import pl.polsl.workflow.manager.client.ui.base.BaseFragment

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
        app.taskWorkerComponent.inject(this)
    }

    override fun setupObservables(viewModel: TaskWorkerViewModel) {
        super.setupObservables(viewModel)
        viewModel.remainingTime.observe {
            view?.workerTaskRemainingTime?.text =
                ("${getString(R.string.timeToCompleteTask)}: ${it?.toHoursMinutesSeconds() ?: "-"}")
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.workerTaskFailure.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_task_to_navigation_task_worker_report_post,
                bundleOf(Pair("task", viewModel.task.value))
            )
        }
        view.workerTaskSuccess.setOnClickListener {
            viewModel.startTask()
        }
        view.taskWorkerFragmentLocalization.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_home_to_mapsFragment,
                bundleOf(Pair("localization", viewModel.task.value?.localization))
            )
        }
    }

}