package pl.polsl.workflow.manager.client.ui.coordinator.account.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account_coordinator_post.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.databinding.FragmentAccountCoordinatorPostBinding
import pl.polsl.workflow.manager.client.model.data.Role
import pl.polsl.workflow.manager.client.model.data.UserPost
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.update
import pl.polsl.workflow.manager.client.util.extension.safeValue

class AccountCoordinatorPostFragment: BaseFragment<AccountCoordinatorPostViewModel>() {

    private lateinit var viewDataBinding: FragmentAccountCoordinatorPostBinding

    override val viewModel: AccountCoordinatorPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
                FragmentAccountCoordinatorPostBinding.inflate(inflater, container, false).apply {
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
        view.coordinatorAccountPostRoleDropdown.update(
            view.context.resources.getStringArray(R.array.coordinatorAccountRoles).toList(),
            roleToPosition(viewModel.selectedRole.safeValue)
        )
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.coordinatorAccountPostRegisterButton.setOnClickListener {
            viewModel.createUser(UserPost(
                    username = view.coordinatorAccountPostUsername.text.toString(),
                    password = view.coordinatorAccountPostPassword.text.toString(),
                    role = viewModel.selectedRole.safeValue
            ))
        }
        view.coordinatorAccountPostRoleDropdown.mSetOnItemSelectedListener {
            viewModel.onRoleSelected(positionToRole(it))
        }
    }

    override fun setupObservables(viewModel: AccountCoordinatorPostViewModel) {
        super.setupObservables(viewModel)
        viewModel.usernameInputError.observe {
            this.view?.coordinatorAccountPostUsernameContainer?.error = it
        }
        viewModel.passwordInputError.observe {
            this.view?.coordinatorAccountPostPasswordContainer?.error = it
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