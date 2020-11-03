package pl.polsl.workflow.manager.client.ui.coordinator.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_groups_coordinator.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentGroupsCoordinatorBinding
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.view.SimpleAdapter
import pl.polsl.workflow.manager.client.ui.view.setupSimpleAdapter
import pl.polsl.workflow.manager.client.util.extension.safeValue

class GroupCoordinatorFragment: BaseFragment<GroupCoordinatorViewModel>() {

    private lateinit var viewDataBinding: FragmentGroupsCoordinatorBinding

    override val viewModel: GroupCoordinatorViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentGroupsCoordinatorBinding.inflate(inflater, container, false).apply {
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
        view.coordinatorGroupGroupList.setupSimpleAdapter {
            val group = viewModel.groups.safeValue[it]
            //todo
        }
        view.coordinatorGroupAddGroup.setOnClickListener {
            findNavController().navigate(
                    R.id.action_navigation_groups_coordinator_to_groupCoordinatorPostFragment
            )
        }
    }

    override fun setupObservables(viewModel: GroupCoordinatorViewModel) {
        super.setupObservables(viewModel)
        viewModel.groups.observe { groups ->
            val list = groups?.map { it.name } ?: listOf()
            (view?.coordinatorGroupGroupList?.adapter as? SimpleAdapter)?.updateSingleList(list)
        }
    }

}