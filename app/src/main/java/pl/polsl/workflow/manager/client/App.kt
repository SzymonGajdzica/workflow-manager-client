package pl.polsl.workflow.manager.client

import android.util.Log
import androidx.multidex.MultiDexApplication
import pl.polsl.workflow.manager.client.ui.di.component.AppComponent
import pl.polsl.workflow.manager.client.ui.di.component.DaggerAppComponent
import pl.polsl.workflow.manager.client.ui.di.module.AppModule

class App: MultiDexApplication() {

    private var mAppComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = mAppComponent ?: DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build().also { mAppComponent = it }

    fun clearDependencies() {
        mAppComponent = null
    }

    companion object {
        fun log(vararg values: Any?){
            Log.d("halo rozwoj", values.joinToString(separator = ", "))
        }
    }

}