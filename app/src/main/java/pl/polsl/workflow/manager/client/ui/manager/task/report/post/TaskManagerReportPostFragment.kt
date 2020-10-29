package pl.polsl.workflow.manager.client.ui.manager.task.report.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentTaskManagerReportPostBinding
import pl.polsl.workflow.manager.client.ui.base.BaseFragment

class TaskManagerReportPostFragment: BaseFragment<TaskManagerReportPostViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskManagerReportPostBinding

    override val viewModel: TaskManagerReportPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
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

}