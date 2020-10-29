package pl.polsl.workflow.manager.client

import android.util.Log
import androidx.multidex.MultiDexApplication
import pl.polsl.workflow.manager.client.ui.di.component.AppComponent
import pl.polsl.workflow.manager.client.ui.di.component.DaggerAppComponent
import pl.polsl.workflow.manager.client.ui.di.module.AppModule

class App: MultiDexApplication() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        fun log(vararg values: Any?){
            Log.d("halo", values.joinToString(separator = " "))
        }
    }

}