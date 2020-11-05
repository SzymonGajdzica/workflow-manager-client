package pl.polsl.workflow.manager.client.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.ui.view.SimpleDialog
import pl.polsl.workflow.manager.client.util.extension.hideKeyboard

abstract class BaseFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.hideKeyboard()
    }

    protected fun showSuccessMessage(description: String) {
        SimpleDialog.create(getString(R.string.success), description).show(parentFragmentManager, "SuccessMessageFragment")
    }

    protected fun showErrorMessage(description: String) {
        SimpleDialog.create(getString(R.string.failure), description).show(parentFragmentManager, "ErrorMessageFragment")
    }

}