package pl.polsl.workflow.manager.client.ui.worker.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_account.view.*
import pl.polsl.workflow.manager.client.databinding.FragmentAccountBinding
import pl.polsl.workflow.manager.client.millisecondsToHoursMinutesSeconds
import pl.polsl.workflow.manager.client.model.data.UserView
import pl.polsl.workflow.manager.client.ui.BaseFragment

class AccountFragment : BaseFragment<AccountViewModel>() {

    private lateinit var viewDataBinding: FragmentAccountBinding

    override val viewModel: AccountViewModel?
        get() = viewDataBinding.viewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentAccountBinding.inflate(inflater, container, false).apply {
            viewModel = ViewModelProvider(this@AccountFragment).get(AccountViewModelImpl::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun setupViews(view: View) {
        super.setupViews(view)
        val userView: UserView? = activity?.intent?.getSerializableExtra("user") as? UserView
        view.accountFragmentUsername.text = userView?.username
        view.accountFragmentLogoutButton.setOnClickListener {
            activity?.let { viewModel?.logout(it) }
        }
    }

    override fun setupObservables(viewModel: AccountViewModel) {
        super.setupObservables(viewModel)
        viewModel.groupView.observe(viewLifecycleOwner) {
            view?.let { view ->
                view.accountFragmentGroupMembersList?.adapter = ArrayAdapter(
                    view.context,
                    android.R.layout.simple_list_item_1,
                    it.workers.map { it.username }
                )
            }
        }
        viewModel.remainingTime.observe(viewLifecycleOwner) {
            view?.accountFragmentRemainingSessionTime?.text = it.millisecondsToHoursMinutesSeconds()
        }
    }

    override fun startLoadingData(viewModel: AccountViewModel) {
        super.startLoadingData(viewModel)
        viewModel.loadAccountDetails()
    }

}