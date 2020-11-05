package pl.polsl.workflow.manager.client.ui.coordinator.group.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_groups_coordinator_post.view.*
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

    override fun setupObservables(viewModel: GroupCoordinatorPostViewModel) {
        super.setupObservables(viewModel)
        viewModel.managers.observe { managers ->
            val managerIndex = managers?.indexOfOrNull(viewModel.selectedManager.value)?.plus(1) ?: 0
            val list = arrayListOf(getString(R.string.notSelected))
            list.addAll(managers?.map { it.username } ?: listOf())
            view?.coordinatorGroupPostManagerDropdown?.update(list, managerIndex)
        }
        viewModel.nameInputError.observe { nameInputError ->
            view?.coordinatorGroupPostNameContainer?.error = nameInputError
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.coordinatorGroupPostCreateButton.setOnClickListener {
            viewModel.createGroup(GroupPost(
                    name = view.coordinatorGroupPostName.text.toString(),
                    manager = viewModel.selectedManager.value
            ))
        }
        view.coordinatorGroupPostManagerDropdown.mSetOnItemSelectedListener {
            viewModel.onManagerSelected(viewModel.managers.value?.getOrNull(it - 1))
        }
    }

}