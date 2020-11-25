package pl.polsl.workflow.manager.client.ui.worker.task.report.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentTaskWorkerReportPostBinding
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReportPost
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.util.extension.safeValue

class TaskWorkerReportPostFragment: BaseFragmentViewModel<TaskWorkerReportPostViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskWorkerReportPostBinding

    override val viewModel: TaskWorkerReportPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentTaskWorkerReportPostBinding.inflate(inflater, container, false).apply {
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
        viewDataBinding.workerTaskReportPostSuccess.setOnClickListener {
            sendReport(true)
        }
        viewDataBinding.workerTaskReportPostFailure.setOnClickListener {
            sendReport(false)
        }
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.descriptionInputError.observe {
            viewDataBinding.workerTaskReportPostDescription.error = it
        }
    }

    private fun sendReport(success: Boolean) {
        val description = viewDataBinding.workerTaskReportPostDescription.text?.toString().toString()
        val taskWorkerReportPost = TaskWorkerReportPost(
                description = description,
                task = viewModel.task.safeValue,
                success = success
        )
        viewModel.sendReport(taskWorkerReportPost)
    }


}