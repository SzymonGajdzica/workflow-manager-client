package pl.polsl.workflow.manager.client.ui.coordinator.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_account_coordinator.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentAccountCoordinatorBinding
import pl.polsl.workflow.manager.client.model.data.Role
import pl.polsl.workflow.manager.client.model.data.UserPatch
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.setupAdapter
import pl.polsl.workflow.manager.client.ui.view.update
import pl.polsl.workflow.manager.client.util.extension.safeValue
import pl.polsl.workflow.manager.client.util.extension.toBundle
import pl.polsl.workflow.manager.client.util.extension.toHoursMinutesSeconds

class AccountCoordinatorFragment: BaseFragmentViewModel<AccountCoordinatorViewModel>() {

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
        val adapter = AccountCoordinatorListAdapter {
            val user = viewModel.users.safeValue[it]
            viewModel.updateUser(user, UserPatch(!user.enabled))
        }
        view.coordinatorAccountUsers.setupAdapter(adapter)
        view.coordinatorAccountUserRoleDropdown.update(
            resources.getStringArray(R.array.coordinatorAccountRoles).toList(),
            roleToPosition(viewModel.selectedRole.safeValue)
        )
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
            viewModel.roleSelected(positionToRole(it))
        }
        view.coordinatorAccountAddUser.setOnClickListener {
            findNavController().navigate(
                    R.id.action_navigation_account_coordinator_to_accountCoordinatorPostFragment,
                    viewModel.selectedRole.safeValue.toBundle()
            )
        }
    }

    private fun positionToRole(position: Int): Int {
        return when(position) {
            0 -> Role.WORKER
            else -> Role.MANAGER
        }
    }

    private fun roleToPosition(taskStatus: Int): Int {
        return when(taskStatus) {
            Role.WORKER -> 0
            else -> 1
        }
    }

}