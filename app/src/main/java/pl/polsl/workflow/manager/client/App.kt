package pl.polsl.workflow.manager.client

import android.util.Log
import androidx.multidex.MultiDexApplication
import pl.polsl.workflow.manager.client.ui.di.account.AccountComponent
import pl.polsl.workflow.manager.client.ui.di.account.DaggerAccountComponent
import pl.polsl.workflow.manager.client.ui.di.login.DaggerLoginComponent
import pl.polsl.workflow.manager.client.ui.di.login.LoginComponent
import pl.polsl.workflow.manager.client.ui.di.module.AppModule
import pl.polsl.workflow.manager.client.ui.di.task.DaggerTaskComponent
import pl.polsl.workflow.manager.client.ui.di.task.TaskComponent
import pl.polsl.workflow.manager.client.ui.di.task.report.DaggerTaskWorkerReportPostComponent
import pl.polsl.workflow.manager.client.ui.di.task.report.TaskWorkerReportPostComponent

class App: MultiDexApplication() {

    val taskWorkerReportPostComponent: TaskWorkerReportPostComponent by lazy {
        DaggerTaskWorkerReportPostComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    val loginComponent: LoginComponent by lazy {
        DaggerLoginComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    val accountComponent: AccountComponent by lazy {
        DaggerAccountComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    val taskComponent: TaskComponent by lazy {
        DaggerTaskComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        fun log(vararg values: Any?){
            Log.d("halo", values.joinToString(separator = " "))
        }
    }

}