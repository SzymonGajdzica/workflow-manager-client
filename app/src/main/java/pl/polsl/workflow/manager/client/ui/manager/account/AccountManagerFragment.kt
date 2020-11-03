package pl.polsl.workflow.manager.client.ui.manager.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account_manager.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentAccountManagerBinding
import pl.polsl.workflow.manager.client.model.data.activeWorkers
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.view.*
import pl.polsl.workflow.manager.client.util.extension.toHoursMinutesSeconds

class AccountManagerFragment: BaseFragment<AccountManagerViewModel>() {

    private lateinit var viewDataBinding: FragmentAccountManagerBinding

    override val viewModel: AccountManagerViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentAccountManagerBinding.inflate(inflater, container, false).apply {
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
        view.managerAccountGroupMembersList.setupSimpleAdapter()
        view.managerAccountGroupDropdown.setupSimpleArrayAdapter(view.context)
    }

    override fun setupObservables(viewModel: AccountManagerViewModel) {
        super.setupObservables(viewModel)
        viewModel.groups.observe { groups ->
            val list = groups?.map { it.name } ?: listOf()
            view?.managerAccountGroupDropdown?.arrayAdapter?.update(list)
        }
        viewModel.selectedGroup.observe { group ->
            val list = group?.activeWorkers?.map { it.username } ?: listOf()
            (view?.managerAccountGroupMembersList?.adapter as? SimpleAdapter)?.updateSingleList(list)
        }
        viewModel.remainingTime.observe {
            view?.managerAccountRemainingSessionTime?.text = it?.toHoursMinutesSeconds()
        }
    }

    override fun setupOnLayoutInteractions(view: View) {
        super.setupOnLayoutInteractions(view)
        view.managerAccountLogoutButton.setOnClickListener {
            logout()
        }
        view.managerAccountGroupDropdown.mSetOnItemSelectedListener {
            viewModel.groupSelected(it)
        }
    }



}