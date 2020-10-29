package pl.polsl.workflow.manager.client.ui.task.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_task_details_worker_report.view.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.formatDate
import pl.polsl.workflow.manager.client.getParcelable
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReport

class TaskDetailsWorkerReport: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_details_worker_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        val taskWorkerReport: TaskWorkerReport = arguments?.getParcelable() ?: return
        view.apply {
            taskDetailsWorkerReportDate.text = taskWorkerReport.date.formatDate()
            taskDetailsWorkerReportDescription.text = taskWorkerReport.description
            if(taskWorkerReport.success) {
                taskDetailsWorkerReportResult.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                taskDetailsWorkerReportResult.text = context.getString(R.string.success)
            } else {
                taskDetailsWorkerReportResult.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                taskDetailsWorkerReportResult.text = context.getString(R.string.failure)
            }
        }
    }

}