package pl.polsl.workflow.manager.client.ui.worker.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentAccountBinding
import pl.polsl.workflow.manager.client.model.data.User
import pl.polsl.workflow.manager.client.setupSimpleAdapterSingle
import pl.polsl.workflow.manager.client.toHoursMinutesSeconds
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.login.LoginActivity

class AccountFragment : BaseFragment<AccountViewModel>() {

    private lateinit var viewDataBinding: FragmentAccountBinding

    override val viewModel: AccountViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentAccountBinding.inflate(inflater, container, false).apply {
            viewModel = createViewModel()
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.accountComponent.inject(this)
    }

    override fun setupViews(view: View) {
        super.setupViews(view)
        val user: User? = activity?.intent?.getParcelableExtra("user") as? User
        view.accountFragmentUsername.text = user?.username
        view.accountFragmentLogoutButton.setOnClickListener {
            viewModel.logout()
            activity?.apply {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun setupObservables(viewModel: AccountViewModel) {
        super.setupObservables(viewModel)
        viewModel.group.observe(viewLifecycleOwner) { group ->
            view?.accountFragmentGroupMembersList?.setupSimpleAdapterSingle(
                    list = group.workers.map { it.username }
            )
        }
        viewModel.remainingTime.observe(viewLifecycleOwner) {
            view?.accountFragmentRemainingSessionTime?.text = it.toHoursMinutesSeconds()
        }
    }

    override fun startLoadingData(viewModel: AccountViewModel) {
        super.startLoadingData(viewModel)
        viewModel.loadAccountDetails()
    }



}