package pl.polsl.workflow.manager.client.ui.coordinator.account.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentAccountCoordinatorPostBinding
import pl.polsl.workflow.manager.client.model.data.UserPost
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.util.extension.safeValue

class AccountCoordinatorPostFragment: BaseFragmentViewModel<AccountCoordinatorPostViewModel>() {

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

    override fun setupOnLayoutInteractions() {
        super.setupOnLayoutInteractions()
        viewDataBinding.coordinatorAccountPostRegisterButton.setOnClickListener {
            viewModel.createUser(UserPost(
                    username = viewDataBinding.coordinatorAccountPostUsername.text.toString(),
                    password = viewDataBinding.coordinatorAccountPostPassword.text.toString(),
                    role = viewModel.selectedRole.safeValue
            ))
        }
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.usernameInputError.observe {
            viewDataBinding.coordinatorAccountPostUsernameContainer.error = it
        }
        viewModel.passwordInputError.observe {
            viewDataBinding.coordinatorAccountPostPasswordContainer.error = it
        }
    }

}