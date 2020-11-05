package pl.polsl.workflow.manager.client.ui.task.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_task_details_manager_report.view.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.AllowableValue
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskManagerReport
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.util.extension.formatDate
import pl.polsl.workflow.manager.client.util.extension.getParcelable
import pl.polsl.workflow.manager.client.util.extension.getParcelableList
import pl.polsl.workflow.manager.client.util.extension.toBundle

class TaskDetailsManagerReport: BaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_details_manager_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        val taskManagerReport: TaskManagerReport = arguments?.getParcelable() ?: return
        val sharedTasks: List<Task> = arguments?.getParcelableList() ?: listOf()
        view.apply {
            val fixTask = taskManagerReport.fixTask
            taskDetailsManagerReportDate.text = taskManagerReport.date.formatDate()
            taskDetailsManagerReportDescription.text = taskManagerReport.description
            if(fixTask != null) {
                taskDetailsManagerReportFixTask.setOnClickListener {
                    when(fixTask) {
                        is AllowableValue.NotAllowed -> showErrorMessage(getString(R.string.notAllowedToBrowseThisResource))
                        is AllowableValue.Allowed -> findNavController().navigate(
                                R.id.action_taskDetailsManagerReport2_to_taskDetailsFragment,
                                listOf(fixTask.value, sharedTasks).toBundle()
                        )
                    }
                }
            } else
                taskDetailsManagerReportFixTask.isEnabled = false
        }
    }

}