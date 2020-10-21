package pl.polsl.workflow.manager.client.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import pl.polsl.workflow.manager.client.model.data.AuthenticationView
import pl.polsl.workflow.manager.client.model.data.checkedToken

class TokenHolderImpl(private val sharedPreferences: SharedPreferences): TokenHolder {

    private val preferencesKey = "authenticationViewKey"

    override var token: AuthenticationView?
        get() {
            return runCatching {
                val json = sharedPreferences.getString(preferencesKey, null)
                val result = Gson().fromJson(json, AuthenticationView::class.java)
                if(result.checkedToken != null) result else null
            }.getOrNull()
        }
        set(value) {
            sharedPreferences.edit(commit = true) {
                putString(preferencesKey, Gson().toJson(value))
            }
        }

}