package pl.polsl.workflow.manager.client.ui.task.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.AllowableValue
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.getSuperTask
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.util.TimerHelper
import pl.polsl.workflow.manager.client.util.extension.*
import java.time.Instant

class TaskDetailsFragment: BaseFragment() {

    private var task: Task? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = arguments?.get<Task>()?.also {
            if(it.startDate != null && it.taskWorkerReport == null)
                TimerHelper.register(lifecycleScope, ::updateRemainingTime)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        val task = task ?: return
        val sharedTasks: List<Task>? = arguments?.getList()
        view.apply {
            val managerReport = task.taskManagerReport
            val workerReport = task.taskWorkerReport
            val superTask = sharedTasks?.let { task.getSuperTask(it) }
            if (managerReport != null) {
                findViewById<View>(R.id.taskDetailsManagerReport).setOnClickListener {
                    findNavController().navigate(
                            R.id.action_taskDetailsFragment_to_taskDetailsManagerReport2,
                            listOf(managerReport, sharedTasks).toBundle()
                    )
                }
            } else
                findViewById<View>(R.id.taskDetailsManagerReport).isEnabled = false
            if (workerReport != null) {
                findViewById<View>(R.id.taskDetailsWorkerReport).setOnClickListener {
                    findNavController().navigate(
                            R.id.action_taskDetailsFragment_to_taskDetailsWorkerReport2,
                            workerReport.toBundle()
                    )
                }
            } else
                findViewById<View>(R.id.taskDetailsWorkerReport).isEnabled = false
            if(superTask != null) {
                findViewById<View>(R.id.taskDetailsSuperTask).setOnClickListener {
                    when(superTask) {
                        is AllowableValue.NotAllowed -> showErrorMessage(getString(R.string.notAllowedToBrowseThisResource))
                        is AllowableValue.Allowed -> findNavController().navigate(
                                R.id.action_taskDetailsFragment_self,
                                listOf(superTask.value, sharedTasks).toBundle()
                        )
                    }
                }
            } else
                findViewById<View>(R.id.taskDetailsSuperTask).isEnabled = false
            findViewById<View>(R.id.taskDetailsLocalization).setOnClickListener {
                findNavController().navigate(
                        R.id.action_taskDetailsFragment_to_destinationMapFragment,
                        task.localization.toBundle()
                )
            }
            findViewById<TextView>(R.id.taskDetailsLocalization).text = ("${getString(R.string.localization)}: ${task.localization.name}")
            findViewById<TextView>(R.id.taskDetailsDescription).text = task.description
            findViewById<TextView>(R.id.taskDetailsName).text = task.name
            if(task.startDate == null)
                findViewById<TextView>(R.id.taskDetailsRemainingTime).text = ("${getString(R.string.remainingTime)}: -")
            else workerReport?.let {
                findViewById<TextView>(R.id.taskDetailsRemainingTime).text = ("${getString(R.string.executionTime)}: ${it.date.minusMillis(task.startDate.toEpochMilli()).toHoursMinutesSeconds()}")
            }
            findViewById<TextView>(R.id.taskDetailsEstimatedExecutionTime).text = ("${getString(R.string.estimatedExecutionTime)}: ${task.estimatedExecutionTime.toHoursMinutesSeconds()}")
            findViewById<TextView>(R.id.taskDetailsDeadline).text = ("${getString(R.string.deadline)}: ${task.deadline.formatDate()}")
            findViewById<TextView>(R.id.taskDetailsAssignedWorker).text = task.assignedWorker.let {
                val username = it?.username ?: getString(R.string.autoAssign)
                "${getString(R.string.assignedWorker)}: $username"
            }
        }
    }

    private fun updateRemainingTime() {
        val task = task ?: return
        val text = task.startDate
                ?.plusMillis(task.estimatedExecutionTime.toEpochMilli())
                ?.minusMillis(Instant.now().toEpochMilli())
                ?.toHoursMinutesSeconds().toString()
        view?.findViewById<TextView>(R.id.taskDetailsRemainingTime)?.text = ("${getString(R.string.remainingTime)}: $text")
    }

}