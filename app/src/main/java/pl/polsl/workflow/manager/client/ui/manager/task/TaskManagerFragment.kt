package pl.polsl.workflow.manager.client.ui.manager.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_task_manager.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentTaskManagerBinding
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.setupSimpleAdapterSingle

class TaskManagerFragment: BaseFragment<TaskManagerViewModel>() {

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
        app.taskManagerComponent.inject(this)
    }

    override fun setupViews(view: View) {
        super.setupViews(view)
        view.managerTaskTaskStatusDropdown.adapter = ArrayAdapter.createFromResource(
                view.context,
                R.array.taskStatuses,
                android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    override fun setupObservables(viewModel: TaskManagerViewModel) {
        super.setupObservables(viewModel)
        viewModel.groups.observe { groups ->
            val context = context
            view?.managerTaskGroupDropdown?.adapter = if(context != null && groups != null) {
                ArrayAdapter(context, android.R.layout.simple_spinner_item, groups.map { it.name }).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
            } else null
        }
        viewModel.tasks.observe { tasks ->
            if(tasks != null) {
                view?.managerTaskTaskList?.setupSimpleAdapterSingle(
                        list = tasks.map { it.name }
                )
            } else {
                view?.managerTaskTaskList?.adapter = null
            }
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
                    bundleOf(Pair("group", selectedGroup))
                )
            }
        }
    }



}