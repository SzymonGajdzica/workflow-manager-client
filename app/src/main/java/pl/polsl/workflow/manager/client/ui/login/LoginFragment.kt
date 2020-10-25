package pl.polsl.workflow.manager.client.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.login_fragment.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.LoginFragmentBinding
import pl.polsl.workflow.manager.client.disableErrorOnWrite
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.worker.WorkerActivity

class LoginFragment : BaseFragment<LoginViewModel>() {

    private lateinit var viewDataBinding: LoginFragmentBinding

    override val viewModel: LoginViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = LoginFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.loginComponent.inject(this)
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.loginFragmentLoginContainer.disableErrorOnWrite()
        view.loginFragmentPasswordContainer.disableErrorOnWrite()
        view.loginFragmentLoginButton.setOnClickListener {
            viewModel.login(
                username = this.view?.loginFragmentLogin?.text.toString(),
                password = this.view?.loginFragmentPassword?.text.toString()
            )
        }
    }

    override fun setupObservables(viewModel: LoginViewModel) {
        super.setupObservables(viewModel)
        viewModel.usernameInputError.observe(viewLifecycleOwner) {
            this.view?.loginFragmentLoginContainer?.error = it
        }
        viewModel.passwordInputError.observe(viewLifecycleOwner) {
            this.view?.loginFragmentPasswordContainer?.error = it
        }
        viewModel.user.observe(viewLifecycleOwner) {
            activity?.run {
                val intent = Intent(this, WorkerActivity::class.java).apply {
                    putExtra("user", it)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    override fun startLoadingData(viewModel: LoginViewModel) {
        super.startLoadingData(viewModel)
        viewModel.tryAutoLogin()
    }

}