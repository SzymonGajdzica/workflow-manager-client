package pl.polsl.workflow.manager.client.ui.coordinator.group.patch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_groups_coordinator_patch.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentGroupsCoordinatorPatchBinding
import pl.polsl.workflow.manager.client.model.data.GroupPatch
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.setupAdapter
import pl.polsl.workflow.manager.client.ui.view.update
import pl.polsl.workflow.manager.client.util.extension.indexOfOrNull
import pl.polsl.workflow.manager.client.util.extension.safeValue

class GroupCoordinatorPatchFragment: BaseFragment<GroupCoordinatorPatchViewModel>() {

    private lateinit var viewDataBinding: FragmentGroupsCoordinatorPatchBinding

    override val viewModel: GroupCoordinatorPatchViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
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

    override fun setupViews(view: View) {
        super.setupViews(view)
        view.coordinatorGroupPatchName.setText(viewModel.initialGroup.safeValue.name)
        val adapter = GroupCoordinatorPatchListAdapter {
            viewModel.onWorkerDeselected(viewModel.selectedWorkers.safeValue[it])
        }
        view.coordinatorGroupPatchWorkerList.setupAdapter(adapter)
    }

    override fun setupObservables(viewModel: GroupCoordinatorPatchViewModel) {
        super.setupObservables(viewModel)
        viewModel.nameInputError.observe { nameInputError ->
            view?.coordinatorGroupPatchNameContainer?.error = nameInputError
        }
        viewModel.managers.observe { managers ->
            val list = arrayListOf(getString(R.string.notSelected))
            list.addAll(managers?.map { it.username } ?: listOf())
            val managerIds = managers?.map { it.id }
            val index = viewModel.selectedManager.value?.let {
                managerIds?.indexOfOrNull(it.id)?.plus(1)
            }
            view?.coordinatorGroupPatchManagerDropdown?.update(list, index)
        }
        viewModel.selectedWorkers.safeObserve {
            (view?.coordinatorGroupPatchWorkerList?.adapter as? GroupCoordinatorPatchListAdapter)?.updateList(it)
        }
        viewModel.remainingWorkers.observe { workers ->
            val list = arrayListOf(getString(R.string.addWorker))
            list.addAll(workers?.map { it.username } ?: listOf())
            view?.coordinatorGroupPatchWorkerDropdown?.update(list, 0)
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.coordinatorGroupPatchManagerDropdown.mSetOnItemSelectedListener {
            viewModel.onManagerSelected(viewModel.managers.value?.getOrNull(it - 1))
        }
        view.coordinatorGroupPatchUpdateButton.setOnClickListener {
            val groupPatch = GroupPatch(
                    name = view.coordinatorGroupPatchName.text.toString(),
                    manager = viewModel.selectedManager.value,
                    workers = viewModel.selectedWorkers.safeValue
            )
            viewModel.updateGroup(viewModel.initialGroup.safeValue, groupPatch)
        }
        view.coordinatorGroupPatchWorkerDropdown.mSetOnItemSelectedListener {
            val worker = viewModel.remainingWorkers.value?.getOrNull(it - 1)
            if(worker != null)
                viewModel.onWorkerSelected(worker)
        }
    }



}