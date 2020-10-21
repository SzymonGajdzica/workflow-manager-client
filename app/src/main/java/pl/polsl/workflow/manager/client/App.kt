package pl.polsl.workflow.manager.client

import android.util.Log
import androidx.multidex.MultiDexApplication

class App: MultiDexApplication() {

    companion object {
        fun log(vararg values: Any?){
            Log.d("halo", values.joinToString(separator = " "))
        }

    }


}