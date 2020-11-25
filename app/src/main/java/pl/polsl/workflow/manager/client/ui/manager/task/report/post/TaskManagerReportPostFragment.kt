package pl.polsl.workflow.manager.client.ui.manager.task.report.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentTaskManagerReportPostBinding
import pl.polsl.workflow.manager.client.model.data.TaskManagerReportPost
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.util.extension.safeValue
import pl.polsl.workflow.manager.client.util.extension.toBundle

class TaskManagerReportPostFragment: BaseFragmentViewModel<TaskManagerReportPostViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskManagerReportPostBinding

    override val viewModel: TaskManagerReportPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentTaskManagerReportPostBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.appComponent.inject(this)
    }

    override fun setupOnLayoutInteractions() {
        super.setupOnLayoutInteractions()
        viewDataBinding.managerTaskReportPostSuccess.setOnClickListener {
            sendReport(false)
        }
        viewDataBinding.managerTaskReportPostFailure.setOnClickListener {
            sendReport(true)
        }
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.descriptionInputError.observe {
            viewDataBinding.managerTaskReportPostDescriptionContainer.error = it
        }
        viewModel.creatingFixTask.observe { creatingFixTask ->
            if(creatingFixTask == true) {
                findNavController().navigate(
                    R.id.action_taskManagerReportPostFragment_to_navigation_task_manager_post,
                    listOf(viewModel.task.safeValue, viewModel.task.safeValue.group).toBundle()
                )
            }
        }
    }

    private fun sendReport(withFixTask: Boolean) {
        val report = TaskManagerReportPost(
            task = viewModel.task.safeValue,
            description = viewDataBinding.managerTaskReportPostDescription.text?.toString() ?: ""
        )
        viewModel.acceptTask(report, withFixTask)
    }

}