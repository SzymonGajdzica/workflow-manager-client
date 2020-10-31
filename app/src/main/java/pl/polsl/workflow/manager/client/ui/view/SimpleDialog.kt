package pl.polsl.workflow.manager.client.ui.view

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pl.polsl.workflow.manager.client.R

class SimpleDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = context ?: return super.onCreateDialog(savedInstanceState)
        val title = arguments?.getString("title")
        val message = arguments?.getString("message")
        return MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.ok)) { _, _ -> }
            .create()
    }

    companion object {
        fun create(title: String, message: String): DialogFragment {
            return SimpleDialog().also {
                it.arguments = bundleOf(
                        Pair("title", title),
                        Pair("message", message)
                )
            }
        }
    }

}