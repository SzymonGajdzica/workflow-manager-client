package pl.polsl.workflow.manager.client.ui.coordinator.localization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentGroupsCoordinatorBinding
import pl.polsl.workflow.manager.client.databinding.FragmentLocalizationsCoordinatorBinding
import pl.polsl.workflow.manager.client.ui.base.BaseFragment
import pl.polsl.workflow.manager.client.ui.coordinator.group.GroupCoordinatorViewModel

class LocalizationCoordinatorFragment: BaseFragment<LocalizationCoordinatorViewModel>() {

    private lateinit var viewDataBinding: FragmentLocalizationsCoordinatorBinding

    override val viewModel: LocalizationCoordinatorViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentLocalizationsCoordinatorBinding.inflate(inflater, container, false).apply {
                viewModel = createViewModel()
                lifecycleOwner = viewLifecycleOwner
            }
        return viewDataBinding.root
    }

    override fun inject(app: App) {
        super.inject(app)
        app.appComponent.inject(this)
    }

}