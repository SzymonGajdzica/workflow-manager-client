package pl.polsl.workflow.manager.client.ui.worker.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_task.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentTaskBinding
import pl.polsl.workflow.manager.client.toHoursMinutesSeconds
import pl.polsl.workflow.manager.client.ui.base.BaseFragment

class TaskFragment : BaseFragment<TaskViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskBinding

    override val viewModel: TaskViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentTaskBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.taskComponent.inject(this)
    }

    override fun startLoadingData(viewModel: TaskViewModel) {
        super.startLoadingData(viewModel)
        viewModel.loadTask()
    }

    override fun setupObservables(viewModel: TaskViewModel) {
        super.setupObservables(viewModel)
        viewModel.remainingTime.observe(viewLifecycleOwner) {
            view?.taskFragmentRemainingTime?.text =
                ("${getString(R.string.timeToCompleteTask)}: ${it?.toHoursMinutesSeconds() ?: "-"}")
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.taskFragmentFailure.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_task_to_navigation_task_worker_report_post,
                bundleOf(Pair("task", viewModel.task.value))
            )
        }
        view.taskFragmentSuccess.setOnClickListener {
            viewModel.startTask()
        }
        view.taskFragmentLocalization.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_home_to_mapsFragment,
                bundleOf(Pair("localization", viewModel.task.value?.localization))
            )
        }
    }

}