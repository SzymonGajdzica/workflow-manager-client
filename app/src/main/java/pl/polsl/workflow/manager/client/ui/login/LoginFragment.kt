package pl.polsl.workflow.manager.client.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.fragment_login.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentLoginBinding
import pl.polsl.workflow.manager.client.model.data.destinationActivityClass
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.util.extension.hasLocationPermission

class LoginFragment : BaseFragmentViewModel<LoginViewModel>() {

    private lateinit var viewDataBinding: FragmentLoginBinding

    override val viewModel: LoginViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if(activity?.hasLocationPermission() == false)
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.loginLoginButton.setOnClickListener {
            viewModel.login(
                username = this.view?.loginUsername?.text.toString(),
                password = this.view?.loginPassword?.text.toString()
            )
        }
    }

    override fun setupObservables(viewModel: LoginViewModel) {
        super.setupObservables(viewModel)
        viewModel.usernameInputError.observe {
            this.view?.loginUsernameContainer?.error = it
        }
        viewModel.passwordInputError.observe {
            this.view?.loginPasswordContainer?.error = it
        }
        viewModel.user.safeObserve {
            activity?.run {
                (application as App).clearDependencies()
                val intent = Intent(this, it.destinationActivityClass).apply {
                    putExtra("user", it)
                }
                startActivity(intent)
                finish()
            }
        }
    }

}