package pl.polsl.workflow.manager.client.util.validator

import android.content.Context
import pl.polsl.workflow.manager.client.R

class InputValidatorImpl(private val context: Context): InputValidator {

    override fun validateBlankText(text: String): String? {
        return if(text.isBlank())
            context.getString(R.string.cannotBeBlank)
        else
            null
    }

    override fun validateUsername(username: String): String? {
        return when {
            username.isBlank() -> context.getString(R.string.cannotBeBlank)
            else -> null
        }
    }

    override fun validatePassword(password: String): String? {
        return when {
            password.length < 3 -> context.getString(R.string.passwordToShort)
            password.isBlank() -> context.getString(R.string.cannotBeBlank)
            else -> null
        }
    }


}