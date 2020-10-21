package pl.polsl.workflow.manager.client.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.showToast

abstract class BaseFragment<T: BaseViewModel>: Fragment() {

    private var lastToast: Toast? = null
    abstract val viewModel: T?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.clearErrorMessages()
        setupViews(view)
        setupOnLayoutInteractions(view)
        viewModel?.apply {
            setupObservables(this)
            startLoadingData(this)
        }
    }

    override fun onDestroyView() {
        lastToast?.cancel()
        lastToast = null
        super.onDestroyView()
    }

    open fun setupViews(view: View) {
        view.findViewById<MaterialButton>(R.id.reloadButton)?.setOnClickListener {
            viewModel?.reloadData()
        }
    }

    open fun setupOnLayoutInteractions(view: View) {

    }

    open fun setupObservables(viewModel: T) {
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
    }

    open fun startLoadingData(viewModel: T) {

    }

    private fun showToast(text: String?) {
        lastToast?.cancel()
        lastToast = text?.let { context?.showToast(it) }
    }

}