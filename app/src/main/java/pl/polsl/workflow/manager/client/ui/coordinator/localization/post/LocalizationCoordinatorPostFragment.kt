package pl.polsl.workflow.manager.client.ui.coordinator.localization.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.databinding.FragmentLocalizationsCoordinatorPostBinding
import pl.polsl.workflow.manager.client.ui.base.BaseFragment

class LocalizationCoordinatorPostFragment: BaseFragment<LocalizationCoordinatorPostViewModel>() {

    private lateinit var viewDataBinding: FragmentLocalizationsCoordinatorPostBinding

    override val viewModel: LocalizationCoordinatorPostViewModel
        get() = viewDataBinding.viewModel ?: throw IllegalStateException()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
                FragmentLocalizationsCoordinatorPostBinding.inflate(inflater, container, false).apply {
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