package pl.polsl.workflow.manager.client.ui.task.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_task_details.view.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.data.AllowableValue
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.getSuperTask
import pl.polsl.workflow.manager.client.ui.view.SimpleDialog
import pl.polsl.workflow.manager.client.util.TimerHelper
import pl.polsl.workflow.manager.client.util.extension.*
import java.time.Instant

class TaskDetailsFragment: Fragment() {

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
        task = arguments?.getParcelable<Task>()?.also {
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
        val sharedTasks: List<Task>? = arguments?.getParcelableList()
        view.apply {
            val managerReport = task.taskManagerReport
            val workerReport = task.taskWorkerReport
            val superTask = sharedTasks?.let { task.getSuperTask(it) }
            if (managerReport != null) {
                taskDetailsManagerReport.setOnClickListener {
                    findNavController().navigate(
                            R.id.action_taskDetailsFragment_to_taskDetailsManagerReport2,
                            listOf(managerReport, sharedTasks).toBundle()
                    )
                }
            } else
                taskDetailsManagerReport.isEnabled = false
            if (workerReport != null) {
                taskDetailsWorkerReport.setOnClickListener {
                    findNavController().navigate(
                            R.id.action_taskDetailsFragment_to_taskDetailsWorkerReport2,
                            workerReport.toBundle()
                    )
                }
            } else
                taskDetailsWorkerReport.isEnabled = false
            if(superTask != null) {
                taskDetailsSuperTask.setOnClickListener {
                    when(superTask) {
                        is AllowableValue.NotAllowed -> SimpleDialog.create(
                                view.context.getString(R.string.error),
                                view.context.getString(R.string.notAllowedToBrowseThisResource)
                        ).show(parentFragmentManager, "ErrorDialog")
                        is AllowableValue.Allowed -> findNavController().navigate(
                                R.id.action_taskDetailsFragment_self,
                                listOf(superTask.value, sharedTasks).toBundle()
                        )
                    }
                }
            } else
                taskDetailsSuperTask.isEnabled = false
            taskDetailsLocalization.setOnClickListener {
                findNavController().navigate(
                        R.id.action_taskDetailsFragment_to_destinationMapFragment,
                        task.localization.toBundle()
                )
            }
            taskDetailsLocalization.text = ("${context.getString(R.string.localization)}: ${task.localization.name}")
            taskDetailsDescription.text = task.description
            taskDetailsName.text = task.name
            if(task.startDate == null)
                taskDetailsRemainingTime.text = ("${context.getString(R.string.remainingTime)}: -")
            else workerReport?.let {
                taskDetailsRemainingTime.text = ("${context.getString(R.string.executionTime)}: ${it.date.minusMillis(task.startDate.toEpochMilli()).toHoursMinutesSeconds()}")
            }
            taskDetailsEstimatedExecutionTime.text = ("${context.getString(R.string.estimatedExecutionTime)}: ${task.estimatedExecutionTime.toHoursMinutesSeconds()}")
            taskDetailsDeadline.text = ("${context.getString(R.string.deadline)}: ${task.deadline.formatDate()}")
            taskDetailsAssignedWorker.text = task.assignedWorker.let {
                val username = it?.username ?: getString(R.string.autoAssign)
                "${context.getString(R.string.assignedWorker)}: $username"
            }
        }
    }

    private fun updateRemainingTime() {
        val task = task ?: return
        val text = task.startDate
                ?.plusMillis(task.estimatedExecutionTime.toEpochMilli())
                ?.minusMillis(Instant.now().toEpochMilli())
                ?.toHoursMinutesSeconds().toString()
        view?.taskDetailsRemainingTime?.text = ("${getString(R.string.remainingTime)}: $text")
    }

}