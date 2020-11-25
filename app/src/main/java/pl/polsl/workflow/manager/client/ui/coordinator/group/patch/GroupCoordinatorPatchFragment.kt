package pl.polsl.workflow.manager.client.ui.coordinator.group.patch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentGroupsCoordinatorPatchBinding
import pl.polsl.workflow.manager.client.model.data.GroupPatch
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.setupAdapter
import pl.polsl.workflow.manager.client.ui.view.update
import pl.polsl.workflow.manager.client.util.extension.indexOfOrNull
import pl.polsl.workflow.manager.client.util.extension.safeValue

class GroupCoordinatorPatchFragment: BaseFragmentViewModel<GroupCoordinatorPatchViewModel>() {

    override val keyboardAboveLayout: Boolean = false
    private lateinit var viewDataBinding: FragmentGroupsCoordinatorPatchBinding

    override val viewModel: GroupCoordinatorPatchViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        viewDataBinding =
                FragmentGroupsCoordinatorPatchBinding.inflate(inflater, container, false).apply {
                    viewModel = createViewModel()
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
        viewDataBinding.coordinatorGroupPatchName.setText(viewModel.initialGroup.safeValue.name)
        val adapter = GroupCoordinatorPatchListAdapter {
            viewModel.onWorkerDeselected(viewModel.selectedWorkers.safeValue[it])
        }
        viewDataBinding.coordinatorGroupPatchWorkerList.setupAdapter(adapter)
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.nameInputError.observe { nameInputError ->
            viewDataBinding.coordinatorGroupPatchNameContainer.error = nameInputError
        }
        viewModel.managers.observe { managers ->
            val list = arrayListOf(getString(R.string.notSelected))
            list.addAll(managers?.map { it.username } ?: listOf())
            val managerIds = managers?.map { it.id }
            val index = viewModel.selectedManager.value?.let {
                managerIds?.indexOfOrNull(it.id)?.plus(1)
            }
            viewDataBinding.coordinatorGroupPatchManagerDropdown.update(list, index)
        }
        viewModel.selectedWorkers.safeObserve {
            (viewDataBinding.coordinatorGroupPatchWorkerList.adapter as? GroupCoordinatorPatchListAdapter)?.updateList(it)
        }
        viewModel.remainingWorkers.observe { workers ->
            val list = workers?.map { it.username } ?: listOf()
            viewDataBinding.coordinatorGroupPatchWorkerDropdown.update(list, null)
        }
    }

    override fun setupOnLayoutInteractions() {
        super.setupOnLayoutInteractions()
        viewDataBinding.coordinatorGroupPatchManagerDropdown.mSetOnItemSelectedListener {
            viewModel.onManagerSelected(viewModel.managers.value?.getOrNull(it - 1))
        }
        viewDataBinding.coordinatorGroupPatchUpdateButton.setOnClickListener {
            val groupPatch = GroupPatch(
                    name = viewDataBinding.coordinatorGroupPatchName.text.toString(),
                    manager = viewModel.selectedManager.value,
                    workers = viewModel.selectedWorkers.safeValue
            )
            viewModel.updateGroup(viewModel.initialGroup.safeValue, groupPatch)
        }
        viewDataBinding.coordinatorGroupPatchWorkerDropdown.mSetOnItemSelectedListener(editable = true) {
            viewModel.remainingWorkers.value?.getOrNull(it)?.let { worker ->
                viewModel.onWorkerSelected(worker)
            }
        }
    }



}