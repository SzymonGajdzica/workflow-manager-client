package pl.polsl.workflow.manager.client.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import pl.polsl.workflow.manager.client.model.data.Authentication

class TokenHolderImpl(
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson
): TokenHolder {

    private val preferencesKey = "authenticationViewKey"

    override var token: Authentication?
        get() {
            return runCatching {
                val json = sharedPreferences.getString(preferencesKey, null)
                gson.fromJson(json, Authentication::class.java)
            }.getOrNull()
        }
        set(value) {
            sharedPreferences.edit(commit = true) {
                if(value != null)
                    putString(preferencesKey, gson.toJson(value))
                else
                    putString(preferencesKey, null)
            }
        }

}