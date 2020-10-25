package pl.polsl.workflow.manager.client.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.showToast
import javax.inject.Inject

abstract class BaseFragment<T: BaseViewModel>: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract val viewModel: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clearErrorMessages()
        setupViews(view)
        setupOnLayoutInteractions(view)
        setupObservables(viewModel)
        startLoadingData(viewModel)
    }

    inline fun<reified T> createViewModel(): T {
        return ViewModelProvider(this, viewModelFactory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (context?.applicationContext as? App)?.let {
            inject(it)
        }
        super.onCreate(savedInstanceState)
    }

    open fun inject(app: App) {

    }

    open fun setupViews(view: View) {
        view.findViewById<MaterialButton>(R.id.reloadButton)?.setOnClickListener {
            viewModel.reloadData()
        }
    }

    open fun setupOnLayoutInteractions(view: View) {

    }

    open fun setupObservables(viewModel: T) {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showToast(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            view?.findViewById<MaterialTextView>(R.id.reloadText)?.text = buildString {
                append(context?.getString(R.string.failedToLoad))
                append("\n")
                append(context?.getString(R.string.cause))
                append(": '")
                append(it)
                append("'")
            }
        }
        viewModel.shouldFinish.observe(viewLifecycleOwner) {
            if(it)
                findNavController().navigateUp()
        }
    }

    open fun startLoadingData(viewModel: T) {

    }

    protected fun showToast(text: String?) {
        text?.let { context?.showToast(it) }
    }

}