package pl.polsl.workflow.manager.client.ui.manager.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_task_manager.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentTaskManagerBinding
import pl.polsl.workflow.manager.client.model.data.TaskStatus
import pl.polsl.workflow.manager.client.model.data.status
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.setupAdapter
import pl.polsl.workflow.manager.client.ui.view.update
import pl.polsl.workflow.manager.client.util.extension.indexOfOrNull
import pl.polsl.workflow.manager.client.util.extension.safeValue
import pl.polsl.workflow.manager.client.util.extension.toBundle

class TaskManagerFragment: BaseFragmentViewModel<TaskManagerViewModel>() {

    private lateinit var viewDataBinding: FragmentTaskManagerBinding

    override val viewModel: TaskManagerViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentTaskManagerBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
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
        App.log(resources.getStringArray(R.array.taskStatuses).toList())
        view.managerTaskTaskStatusDropdown.update(resources.getStringArray(R.array.taskStatuses).toList(), viewModel.selectedTaskStatus.safeValue)
        val adapter = TaskManagerListAdapter(
                itemClickListener = ::taskSelected,
                actionButtonClickListener = ::taskActionClicked
        )
        view.managerTaskTaskList.setupAdapter(adapter)
    }

    private fun taskSelected(index: Int) {
        val task = viewModel.tasks.safeValue[index]
        findNavController().navigate(
                R.id.action_navigation_task_manager_to_task_details_navigation,
                listOf(task, viewModel.getSharedTasks(task)).toBundle()
        )
    }

    private fun taskActionClicked(index: Int) {
        val task = viewModel.tasks.safeValue[index]
        when(task.status) {
            TaskStatus.CREATED -> viewModel.removeTask(task)
            TaskStatus.FINISHED -> {
                findNavController().navigate(
                        R.id.action_navigation_task_manager_to_taskManagerReportPostFragment,
                        listOf(task, viewModel.getSharedTasks(task)).toBundle()
                )
            }
        }
    }

    override fun setupObservables(viewModel: TaskManagerViewModel) {
        super.setupObservables(viewModel)
        viewModel.groups.observe { groups ->
            val groupIndex = groups?.indexOfOrNull(viewModel.selectedGroup.value) ?: 0
            val list = groups?.map { it.name } ?: listOf()
            view?.managerTaskGroupDropdown?.update(list, groupIndex)
        }
        viewModel.tasks.observe { tasks ->
            val list = tasks ?: listOf()
            (view?.managerTaskTaskList?.adapter as? TaskManagerListAdapter)?.updateList(list)
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.managerTaskGroupDropdown.mSetOnItemSelectedListener { position ->
            viewModel.groups.value?.get(position)?.let { viewModel.groupSelected(it) }
        }
        view.managerTaskTaskStatusDropdown.mSetOnItemSelectedListener {
            viewModel.taskStatusSelected(it)
        }
        view.managerTaskAddTask.setOnClickListener {
            val selectedGroup = viewModel.selectedGroup.value
            if(selectedGroup != null) {
                findNavController().navigate(
                        R.id.action_navigation_task_manager_to_navigation_task_manager_post,
                        selectedGroup.toBundle()
                )
            }
        }
    }



}