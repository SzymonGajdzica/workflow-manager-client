package pl.polsl.workflow.manager.client.ui.coordinator.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_account_coordinator.view.*
import kotlinx.android.synthetic.main.fragment_account_manager.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentAccountCoordinatorBinding
import pl.polsl.workflow.manager.client.databinding.FragmentTaskManagerBinding
import pl.polsl.workflow.manager.client.model.data.Role
import pl.polsl.workflow.manager.client.model.data.UserPatch
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.manager.task.TaskManagerViewModel
import pl.polsl.workflow.manager.client.ui.view.*
import pl.polsl.workflow.manager.client.util.extension.safeValue
import pl.polsl.workflow.manager.client.util.extension.toHoursMinutesSeconds

class AccountCoordinatorFragment: BaseFragment<AccountCoordinatorViewModel>() {

    private lateinit var viewDataBinding: FragmentAccountCoordinatorBinding

    override val viewModel: AccountCoordinatorViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentAccountCoordinatorBinding.inflate(inflater, container, false).apply {
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
        view.coordinatorAccountUserRoleDropdown.setupSimpleArrayAdapter(view.context)
        val adapter = AccountCoordinatorListAdapter {
            val user = viewModel.users.safeValue[it]
            viewModel.updateUser(user, UserPatch(!user.enabled))
        }
        view.coordinatorAccountUsers.setupAdapter(adapter)
        view.coordinatorAccountUserRoleDropdown.arrayAdapter?.update(view.context.resources.getStringArray(R.array.coordinatorAccountRoles).toList())
    }

    override fun setupObservables(viewModel: AccountCoordinatorViewModel) {
        super.setupObservables(viewModel)
        viewModel.remainingTime.observe {
            view?.coordinatorAccountRemainingSessionTime?.text = it?.toHoursMinutesSeconds()
        }
        viewModel.users.observe { users ->
            val list = users ?: listOf()
            (view?.coordinatorAccountUsers?.adapter as? AccountCoordinatorListAdapter)?.updateList(list)
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.coordinatorAccountLogoutButton.setOnClickListener {
            logout()
        }
        view.coordinatorAccountUserRoleDropdown.mSetOnItemSelectedListener {
            val role = when(it) {
                0 -> Role.WORKER
                else -> Role.MANAGER
            }
            viewModel.roleSelected(role)
        }
        view.coordinatorAccountAddUser.setOnClickListener {
            findNavController().navigate(
                    R.id.action_navigation_account_coordinator_to_accountCoordinatorPostFragment
            )
        }
    }

}