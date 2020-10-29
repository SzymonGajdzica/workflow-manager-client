package pl.polsl.workflow.manager.client.ui.manager.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_account_manager.view.*
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentAccountManagerBinding
import pl.polsl.workflow.manager.client.toHoursMinutesSeconds
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.setupSimpleAdapterSingle

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

    override fun setupObservables(viewModel: AccountManagerViewModel) {
        super.setupObservables(viewModel)
        viewModel.groups.observe { groups ->
            val context = context
            view?.managerAccountGroupDropdown?.adapter = if(context != null && groups != null) {
                ArrayAdapter(context, android.R.layout.simple_spinner_item, groups.map { it.name }).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
            } else null
        }
        viewModel.selectedGroup.observe { group ->
            if(group != null) {
                view?.managerAccountGroupMembersList?.setupSimpleAdapterSingle(
                    list = group.workers.map { it.username }
                )
            } else
                view?.managerAccountGroupMembersList?.adapter = null
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