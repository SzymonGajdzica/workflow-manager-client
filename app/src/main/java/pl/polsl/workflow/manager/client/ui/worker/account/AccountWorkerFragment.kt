package pl.polsl.workflow.manager.client.ui.worker.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account_worker.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentAccountWorkerBinding
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.toHoursMinutesSeconds
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.login.LoginActivity
import pl.polsl.workflow.manager.client.ui.view.setupSimpleAdapterSingle

class AccountWorkerFragment : BaseFragment<AccountWorkerViewModel>() {

    private lateinit var viewDataBinding: FragmentAccountWorkerBinding

    override val viewModel: AccountWorkerViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentAccountWorkerBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.accountWorkerComponent.inject(this)
    }

    override fun setupViews(view: View) {
        super.setupViews(view)
        val user: User? = activity?.intent?.getParcelableExtra("user") as? User
        view.workerAccountUsername.text = user?.username
        view.workerAccountLogoutButton.setOnClickListener {
            viewModel.logout()
            activity?.apply {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun setupObservables(viewModel: AccountWorkerViewModel) {
        super.setupObservables(viewModel)
        viewModel.group.observe { group ->
            if(group != null) {
                view?.workerAccountGroupMembersList?.setupSimpleAdapterSingle(
                        list = group.workers.map { it.username }
                )
            } else {
                view?.workerAccountGroupMembersList?.adapter = null
            }
        }
        viewModel.remainingTime.observe {
            view?.workerAccountRemainingSessionTime?.text = it?.toHoursMinutesSeconds()
        }
    }

}