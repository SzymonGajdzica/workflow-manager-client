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
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.view.arrayAdapter
import pl.polsl.workflow.manager.client.ui.view.setupSimpleArrayAdapter
import pl.polsl.workflow.manager.client.ui.view.update

class GroupCoordinatorPostFragment: BaseFragment<GroupCoordinatorPostViewModel>() {

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

    override fun setupViews(view: View) {
        super.setupViews(view)
        view.coordinatorGroupPostManagerDropdown.setupSimpleArrayAdapter(view.context)
    }

    override fun setupObservables(viewModel: GroupCoordinatorPostViewModel) {
        super.setupObservables(viewModel)
        viewModel.managers.observe { managers ->
            val list = arrayListOf(getString(R.string.notSelected))
            list.addAll(managers?.map { it.username } ?: listOf())
            view?.coordinatorGroupPostManagerDropdown?.arrayAdapter?.update(list)
        }
        viewModel.nameInputError.observe { nameInputError ->
            view?.coordinatorGroupPostNameContainer?.error = nameInputError
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.coordinatorGroupPostCreateButton.setOnClickListener {
            val managerIndex = view.coordinatorGroupPostManagerDropdown.selectedItemPosition - 1
            viewModel.createGroup(GroupPost(
                    name = view.coordinatorGroupPostName.text.toString(),
                    manager = if(managerIndex > 0) viewModel.managers.value?.get(managerIndex) else null
            ))
        }
    }

}