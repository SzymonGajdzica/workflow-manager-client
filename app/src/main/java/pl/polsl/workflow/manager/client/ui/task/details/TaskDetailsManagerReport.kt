package pl.polsl.workflow.manager.client.ui.task.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.AllowableValue
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskManagerReport
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.util.extension.formatDate
import pl.polsl.workflow.manager.client.util.extension.get
import pl.polsl.workflow.manager.client.util.extension.getList
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
        val taskManagerReport: TaskManagerReport = arguments?.get() ?: return
        val sharedTasks: List<Task> = arguments?.getList() ?: listOf()
        view.apply {
            val fixTask = taskManagerReport.fixTask
            findViewById<TextView>(R.id.taskDetailsManagerReportDate).text = taskManagerReport.date.formatDate()
            findViewById<TextView>(R.id.taskDetailsManagerReportDescription).text = taskManagerReport.description
            if(fixTask != null) {
                findViewById<View>(R.id.taskDetailsManagerReportFixTask).setOnClickListener {
                    when(fixTask) {
                        is AllowableValue.NotAllowed -> showErrorMessage(getString(R.string.notAllowedToBrowseThisResource))
                        is AllowableValue.Allowed -> findNavController().navigate(
                                R.id.action_taskDetailsManagerReport2_to_taskDetailsFragment,
                                listOf(fixTask.value, sharedTasks).toBundle()
                        )
                    }
                }
            } else
                findViewById<View>(R.id.taskDetailsManagerReportFixTask).isEnabled = false
        }
    }

}