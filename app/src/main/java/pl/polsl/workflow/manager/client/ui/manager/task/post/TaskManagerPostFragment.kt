package pl.polsl.workflow.manager.client.ui.manager.task.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentTaskManagerPostBinding
import pl.polsl.workflow.manager.client.model.data.TaskPost
import pl.polsl.workflow.manager.client.model.data.activeWorkers
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.ui.shared.SharedViewModelImpl
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.showDateTimePicker
import pl.polsl.workflow.manager.client.ui.view.showTimePicker
import pl.polsl.workflow.manager.client.ui.view.update
import pl.polsl.workflow.manager.client.util.extension.formatDate
import pl.polsl.workflow.manager.client.util.extension.indexOfOrNull
import pl.polsl.workflow.manager.client.util.extension.safeValue
import pl.polsl.workflow.manager.client.util.extension.toHoursMinutesSeconds

class TaskManagerPostFragment: BaseFragmentViewModel<TaskManagerPostViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskManagerPostBinding

    override val viewModel: TaskManagerPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentTaskManagerPostBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            sharedViewModel = createSharedViewModel<SharedViewModelImpl>()
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.appComponent.inject(this)
    }

    override fun setupViews() {
        super.setupViews()
        val list = arrayListOf(getString(R.string.autoAssign))
        list.addAll(viewModel.group.safeValue.activeWorkers.map { it.username })
        val index = viewModel.group.safeValue.activeWorkers.indexOfOrNull(viewModel.selectedWorker.value)?.plus(1) ?: 0
        viewDataBinding.managerTaskPostWorkerDropdown.update(
            list,
            index
        )
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.nameInputError.observe {
            viewDataBinding.managerTaskPostNameContainer.error = it
        }
        viewModel.nameInputError.observe {
            viewDataBinding.managerTaskPostDescriptionContainer.error = it
        }
        viewModel.deadline.safeObserve {
            viewDataBinding.managerTaskPostDeadline.text =
                ("${getString(R.string.deadline)}: ${it.formatDate()}")
        }
        viewModel.executionTime.safeObserve {
            viewDataBinding.managerTaskPostExecutionTime.text =
                ("${getString(R.string.executionTime)}: ${it.toHoursMinutesSeconds()}")
        }
    }

    override fun setupOnLayoutInteractions() {
        super.setupOnLayoutInteractions()
        viewDataBinding.managerTaskPostDeadline.setOnClickListener {
            showDateTimePicker("task_manager_deadline_picker", viewModel.deadline.value) { instant ->
                viewModel.updateDeadline(instant)
            }
        }
        viewDataBinding.managerTaskPostExecutionTime.setOnClickListener {
            showTimePicker("task_manager_deadline_picker", viewModel.executionTime.value) { instant ->
                viewModel.updateExecutionTime(instant)
            }
        }
        viewDataBinding.managerTaskPostWorkerDropdown.mSetOnItemSelectedListener {
            viewModel.updateSelectedWorker(viewModel.group.safeValue.activeWorkers.getOrNull(it - 1))
        }
        viewDataBinding.managerTaskPostLocalization.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_task_manager_post_to_mapSelectFragment)
        }
        viewDataBinding.managerTaskPostCreate.setOnClickListener { _ ->
            val localization = viewDataBinding.sharedViewModel?.localization?.value
                    ?: return@setOnClickListener showErrorMessage(getString(R.string.selectLocalization))
            val taskPost = TaskPost(
                    group = viewModel.group.safeValue,
                    name = viewDataBinding.managerTaskPostName.text.toString(),
                    description = viewDataBinding.managerTaskPostDescription.text.toString(),
                    deadline = viewModel.deadline.safeValue,
                    subTask = viewModel.subTask.value,
                    localization = localization,
                    estimatedExecutionTime = viewModel.executionTime.safeValue,
                    worker = viewModel.selectedWorker.value
            )
            viewModel.createTask(taskPost)
        }
    }

}