package pl.polsl.workflow.manager.client.ui.worker.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentAccountWorkerBinding
import pl.polsl.workflow.manager.client.model.data.activeWorkers
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.ui.view.SimpleAdapter
import pl.polsl.workflow.manager.client.ui.view.setupSimpleAdapter
import pl.polsl.workflow.manager.client.util.extension.toHoursMinutesSeconds

class AccountWorkerFragment : BaseFragmentViewModel<AccountWorkerViewModel>() {

    private lateinit var viewDataBinding: FragmentAccountWorkerBinding

    override val viewModel: AccountWorkerViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
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

    override fun setupViews() {
        super.setupViews()
        viewDataBinding.workerAccountGroupMembersList.setupSimpleAdapter()
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.group.observe { group ->
            val list = group?.activeWorkers?.map { it.username } ?: listOf()
            (viewDataBinding.workerAccountGroupMembersList.adapter as? SimpleAdapter)?.updateSingleList(list)
        }
        viewModel.remainingTime.observe {
            viewDataBinding.workerAccountRemainingSessionTime.text = it?.toHoursMinutesSeconds()
        }
    }

    override fun setupOnLayoutInteractions() {
        super.setupOnLayoutInteractions()
        viewDataBinding.workerAccountLogoutButton.setOnClickListener {
            logout()
        }
    }

}