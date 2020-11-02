package pl.polsl.workflow.manager.client.util.validator

interface InputValidator {

    fun validateBlankText(text: String): String?

    fun validateUsername(username: String): String?

    fun validatePassword(password: String): String?

}