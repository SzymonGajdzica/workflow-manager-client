package pl.polsl.workflow.manager.client.ui.task.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReport
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.util.extension.formatDate
import pl.polsl.workflow.manager.client.util.extension.get

class TaskDetailsWorkerReport: BaseFragment() {

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
        val taskWorkerReport: TaskWorkerReport = arguments?.get() ?: return
        view.apply {
            findViewById<TextView>(R.id.taskDetailsWorkerReportDate).text = taskWorkerReport.date.formatDate()
            findViewById<TextView>(R.id.taskDetailsWorkerReportDescription).text = taskWorkerReport.description
            if(taskWorkerReport.success) {
                findViewById<TextView>(R.id.taskDetailsWorkerReportResult).setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                findViewById<TextView>(R.id.taskDetailsWorkerReportResult).text = getString(R.string.success)
            } else {
                findViewById<TextView>(R.id.taskDetailsWorkerReportResult).setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                findViewById<TextView>(R.id.taskDetailsWorkerReportResult).text = getString(R.string.failure)
            }
        }
    }

}