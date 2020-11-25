package pl.polsl.workflow.manager.client.ui.manager.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentAccountManagerBinding
import pl.polsl.workflow.manager.client.model.data.activeWorkers
import pl.polsl.workflow.manager.client.ui.base.BaseFragmentViewModel
import pl.polsl.workflow.manager.client.ui.view.SimpleAdapter
import pl.polsl.workflow.manager.client.ui.view.mSetOnItemSelectedListener
import pl.polsl.workflow.manager.client.ui.view.setupSimpleAdapter
import pl.polsl.workflow.manager.client.ui.view.update
import pl.polsl.workflow.manager.client.util.extension.indexOfOrNull
import pl.polsl.workflow.manager.client.util.extension.safeValue
import pl.polsl.workflow.manager.client.util.extension.toHoursMinutesSeconds

class AccountManagerFragment: BaseFragmentViewModel<AccountManagerViewModel>() {

    private lateinit var viewDataBinding: FragmentAccountManagerBinding

    override val viewModel: AccountManagerViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    override fun setupViews() {
        super.setupViews()
        viewDataBinding.managerAccountGroupMembersList.setupSimpleAdapter()
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.groups.observe { groups ->
            val groupIndex = groups?.indexOfOrNull(viewModel.selectedGroup.value) ?: 0
            val list = groups?.map { it.name } ?: listOf()
            viewDataBinding.managerAccountGroupDropdown.update(list, groupIndex)
        }
        viewModel.selectedGroup.observe { group ->
            val list = group?.activeWorkers?.map { it.username } ?: listOf()
            (viewDataBinding.managerAccountGroupMembersList.adapter as? SimpleAdapter)?.updateSingleList(list)
        }
        viewModel.remainingTime.observe {
            viewDataBinding.managerAccountRemainingSessionTime.text = it?.toHoursMinutesSeconds()
        }
    }

    override fun setupOnLayoutInteractions() {
        super.setupOnLayoutInteractions()
        viewDataBinding.managerAccountLogoutButton.setOnClickListener {
            logout()
        }
        viewDataBinding.managerAccountGroupDropdown.mSetOnItemSelectedListener {
            viewModel.groupSelected(viewModel.groups.safeValue[it])
        }
    }



}