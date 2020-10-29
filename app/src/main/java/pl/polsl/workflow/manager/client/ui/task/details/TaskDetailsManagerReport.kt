package pl.polsl.workflow.manager.client.ui.task.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_task_details_manager_report.view.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.formatDate
import pl.polsl.workflow.manager.client.getParcelable
import pl.polsl.workflow.manager.client.model.data.TaskManagerReport
import pl.polsl.workflow.manager.client.toBundle

class TaskDetailsManagerReport: Fragment() {

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
        view.apply {
            val fixTask = taskManagerReport.fixTask
            taskDetailsManagerReportDate.text = taskManagerReport.date.formatDate()
            taskDetailsManagerReportDescription.text = taskManagerReport.description
            if(fixTask != null) {
                taskDetailsManagerReportFixTask.setOnClickListener {
                    findNavController().navigate(
                            R.id.action_taskDetailsManagerReport2_to_taskDetailsFragment,
                            fixTask.toBundle()
                    )
                }
            } else
                taskDetailsManagerReportFixTask.isEnabled = false
        }
    }

}