package pl.polsl.workflow.manager.client.ui.worker.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account_worker.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentAccountWorkerBinding
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.view.SimpleAdapter
import pl.polsl.workflow.manager.client.ui.view.setupSimpleAdapter
import pl.polsl.workflow.manager.client.util.extension.toHoursMinutesSeconds

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
        app.appComponent.inject(this)
    }

    override fun setupViews(view: View) {
        super.setupViews(view)
        view.workerAccountGroupMembersList.setupSimpleAdapter()
    }

    override fun setupObservables(viewModel: AccountWorkerViewModel) {
        super.setupObservables(viewModel)
        viewModel.group.observe { group ->
            val list = group?.workers?.map { it.username } ?: listOf()
            (view?.workerAccountGroupMembersList?.adapter as? SimpleAdapter)?.updateSingleList(list)
        }
        viewModel.remainingTime.observe {
            view?.workerAccountRemainingSessionTime?.text = it?.toHoursMinutesSeconds()
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.workerAccountLogoutButton.setOnClickListener {
            logout()
        }
    }

}