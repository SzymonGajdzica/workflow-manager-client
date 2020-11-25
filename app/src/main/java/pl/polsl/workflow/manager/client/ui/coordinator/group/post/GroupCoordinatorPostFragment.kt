package pl.polsl.workflow.manager.client.ui.coordinator.group.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentGroupsCoordinatorPostBinding
import pl.polsl.workflow.manager.client.model.data.GroupPost
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.update
import pl.polsl.workflow.manager.client.util.extension.indexOfOrNull

class GroupCoordinatorPostFragment: BaseFragmentViewModel<GroupCoordinatorPostViewModel>() {

    private lateinit var viewDataBinding: FragmentGroupsCoordinatorPostBinding

    override val viewModel: GroupCoordinatorPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
                FragmentGroupsCoordinatorPostBinding.inflate(inflater, container, false).apply {
                    viewModel = createViewModel()
                    lifecycleOwner = viewLifecycleOwner
                }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.appComponent.inject(this)
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.managers.observe { managers ->
            val managerIndex = managers?.indexOfOrNull(viewModel.selectedManager.value)?.plus(1) ?: 0
            val list = arrayListOf(getString(R.string.notSelected))
            list.addAll(managers?.map { it.username } ?: listOf())
            viewDataBinding.coordinatorGroupPostManagerDropdown.update(list, managerIndex)
        }
        viewModel.nameInputError.observe { nameInputError ->
            viewDataBinding.coordinatorGroupPostNameContainer.error = nameInputError
        }
    }

    override fun setupOnLayoutInteractions() {
        super.setupOnLayoutInteractions()
        viewDataBinding.coordinatorGroupPostCreateButton.setOnClickListener {
            viewModel.createGroup(GroupPost(
                    name = viewDataBinding.coordinatorGroupPostName.text.toString(),
                    manager = viewModel.selectedManager.value
            ))
        }
        viewDataBinding.coordinatorGroupPostManagerDropdown.mSetOnItemSelectedListener {
            viewModel.onManagerSelected(viewModel.managers.value?.getOrNull(it - 1))
        }
    }

}