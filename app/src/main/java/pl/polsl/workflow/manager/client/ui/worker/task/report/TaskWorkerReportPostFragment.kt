package pl.polsl.workflow.manager.client.ui.worker.task.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_task_worker_report_post.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentTaskWorkerReportPostBinding
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReportPost
import pl.polsl.workflow.manager.client.ui.base.BaseFragment

class TaskWorkerReportPostFragment: BaseFragment<TaskWorkerReportPostViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskWorkerReportPostBinding

    override val viewModel: TaskWorkerReportPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentTaskWorkerReportPostBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.taskWorkerReportPostComponent.inject(this)
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.taskWorkerReportPostFragmentSuccess.setOnClickListener {
            sendReport(true)
        }
        view.taskWorkerReportPostFragmentFailure.setOnClickListener {
            sendReport(false)
        }
    }

    override fun setupObservables(viewModel: TaskWorkerReportPostViewModel) {
        super.setupObservables(viewModel)
        viewModel.descriptionInputError.observe(viewLifecycleOwner) {
            view?.taskWorkerReportPostFragmentTaskDescription?.error = it
        }
    }

    private fun sendReport(success: Boolean) {
        val task: Task? = arguments?.getParcelable("task")
        val description = view?.taskWorkerReportPostFragmentTaskDescription?.text?.toString()
        if(task == null || description == null)
            return showToast(context?.getString(R.string.unknownError))
        val taskWorkerReportPost = TaskWorkerReportPost(
                description = description,
                task = task,
                success = success
        )
        viewModel.sendReport(taskWorkerReportPost)
    }


}