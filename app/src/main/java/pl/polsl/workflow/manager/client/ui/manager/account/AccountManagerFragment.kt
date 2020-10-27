package pl.polsl.workflow.manager.client.ui.manager.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.workflow.manager.client.databinding.FragmentAccountManagerBinding
import pl.polsl.workflow.manager.client.ui.base.BaseFragment

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

}