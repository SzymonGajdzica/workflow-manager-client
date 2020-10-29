package pl.polsl.workflow.manager.client.ui.manager.task.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_task_manager_post.view.*
import pl.polsl.workflow.manager.client.*
import pl.polsl.workflow.manager.client.databinding.FragmentTaskManagerPostBinding
import pl.polsl.workflow.manager.client.model.data.TaskPost
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.shared.SharedViewModelImpl
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.showDateTimePicker
import pl.polsl.workflow.manager.client.ui.view.showTimePicker

class TaskManagerPostFragment: BaseFragment<TaskManagerPostViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskManagerPostBinding

    override val viewModel: TaskManagerPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun setupViews(view: View) {
        super.setupViews(view)
        val entries = arrayListOf(view.context.getString(R.string.autoAssign))
        entries.addAll(viewModel.group.safeValue.workers.map { it.username })
        view.managerTaskPostWorkerDropdown.adapter = ArrayAdapter(
                view.context,
                android.R.layout.simple_spinner_item,
                entries
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    override fun setupObservables(viewModel: TaskManagerPostViewModel) {
        super.setupObservables(viewModel)
        viewModel.nameInputError.observe {
            view?.managerTaskPostNameContainer?.error = it
        }
        viewModel.nameInputError.observe {
            view?.managerTaskPostDescriptionContainer?.error = it
        }
        viewModel.deadline.safeObserve {
            view?.managerTaskPostDeadline?.text =
                ("${context?.getString(R.string.deadline)}: ${it.formatDate()}")
        }
        viewModel.executionTime.safeObserve {
            view?.managerTaskPostExecutionTime?.text =
                ("${context?.getString(R.string.executionTime)}: ${it.toHoursMinutesSeconds()}")
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.managerTaskPostDeadline.setOnClickListener {
            showDateTimePicker("task_manager_deadline_picker", viewModel.deadline.value) { instant ->
                viewModel.updateDeadline(instant)
            }
        }
        view.managerTaskPostExecutionTime.setOnClickListener {
            showTimePicker("task_manager_deadline_picker", viewModel.executionTime.value) { instant ->
                viewModel.updateExecutionTime(instant)
            }
        }
        view.managerTaskPostWorkerDropdown.mSetOnItemSelectedListener {
            viewModel.updateSelectedWorkerIndex(if(it == 0) null else it - 1)
        }
        view.managerTaskPostLocalization.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_task_manager_post_to_mapSelectFragment)
        }
        view.managerTaskPostCreate.setOnClickListener {
            val localization = viewDataBinding.sharedViewModel?.localization?.value
                    ?: return@setOnClickListener showToast(R.string.selectLocalization)
            val taskPost = TaskPost(
                    group = viewModel.group.safeValue,
                    name = view.managerTaskPostName.text.toString(),
                    description = view.managerTaskPostDescription.text.toString(),
                    deadline = viewModel.deadline.safeValue,
                    subTask = viewModel.subTask.value,
                    localization = localization,
                    estimatedExecutionTime = viewModel.executionTime.safeValue,
                    worker = viewModel.selectedWorkerIndex.value?.let { viewModel.group.safeValue.workers[it] }
            )
            viewModel.createTask(taskPost)
        }
    }

}