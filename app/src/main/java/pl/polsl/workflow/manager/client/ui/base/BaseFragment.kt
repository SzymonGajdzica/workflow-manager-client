package pl.polsl.workflow.manager.client.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.showToast
import pl.polsl.workflow.manager.client.ui.login.LoginActivity
import javax.inject.Inject

abstract class BaseFragment<T: BaseViewModel>: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract val viewModel: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.intent?.let { viewModel.updateSharedArguments(it) }
        arguments?.let { viewModel.updateArguments(it) }
        viewModel.clearErrorMessages()
        setupViews(view)
        setupOnLayoutInteractions(view)
        setupObservables(viewModel)
        if(viewModel.error.value == null)
            viewModel.reloadData()
    }

    protected inline fun<reified T: ViewModel> createViewModel(): T {
        return ViewModelProvider(this, viewModelFactory).get()
    }

    protected inline fun<reified T: ViewModel> createSharedViewModel(): T {
        return ViewModelProvider(requireActivity()).get(T::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context?.applicationContext as? App)?.let {
            inject(it)
        }
    }

    protected fun <T>LiveData<T>.observe(observer: (T?) -> Unit) {
        observe(viewLifecycleOwner, observer)
    }

    protected fun <T>LiveData<T>.safeObserve(observer: (T) -> Unit) {
        observe(viewLifecycleOwner, observer)
    }

    open fun inject(app: App) {

    }

    open fun setupViews(view: View) {
        view.findViewById<MaterialButton>(R.id.reloadButton)?.setOnClickListener {
            viewModel.clearErrorString()
            viewModel.reloadData()
        }
    }

    open fun setupOnLayoutInteractions(view: View) {

    }

    open fun setupObservables(viewModel: T) {
        viewModel.errorMessage.observe {
            showToast(it)
        }
        viewModel.error.observe {
            view?.findViewById<MaterialTextView>(R.id.reloadText)?.text = buildString {
                append(context?.getString(R.string.failedToLoad))
                append("\n")
                append(context?.getString(R.string.cause))
                append(": '")
                append(it)
                append("'")
            }
        }
        viewModel.shouldFinish.safeObserve {
            if(it)
                findNavController().navigateUp()
        }
    }

    protected fun showToast(@StringRes resId: Int) {
        context?.showToast(resId)
    }

    protected fun showToast(text: String?) {
        text?.let { context?.showToast(it) }
    }

    protected fun logout() {
        (viewModel as? AccountViewModel)?.logout()
        activity?.apply {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}