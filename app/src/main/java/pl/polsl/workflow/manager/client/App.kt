package pl.polsl.workflow.manager.client

import android.util.Log
import androidx.multidex.MultiDexApplication
import pl.polsl.workflow.manager.client.ui.di.login.DaggerLoginComponent
import pl.polsl.workflow.manager.client.ui.di.login.LoginComponent
import pl.polsl.workflow.manager.client.ui.di.manager.task.DaggerTaskManagerComponent
import pl.polsl.workflow.manager.client.ui.di.manager.task.TaskManagerComponent
import pl.polsl.workflow.manager.client.ui.di.manager.task.post.DaggerTaskManagerPostComponent
import pl.polsl.workflow.manager.client.ui.di.manager.task.post.TaskManagerPostComponent
import pl.polsl.workflow.manager.client.ui.di.map.DaggerMapSelectComponent
import pl.polsl.workflow.manager.client.ui.di.map.MapSelectComponent
import pl.polsl.workflow.manager.client.ui.di.module.AppModule
import pl.polsl.workflow.manager.client.ui.di.worker.account.AccountWorkerComponent
import pl.polsl.workflow.manager.client.ui.di.worker.account.DaggerAccountWorkerComponent
import pl.polsl.workflow.manager.client.ui.di.worker.task.DaggerTaskWorkerComponent
import pl.polsl.workflow.manager.client.ui.di.worker.task.TaskWorkerComponent
import pl.polsl.workflow.manager.client.ui.di.worker.task.report.DaggerTaskWorkerReportPostComponent
import pl.polsl.workflow.manager.client.ui.di.worker.task.report.TaskWorkerReportPostComponent

class App: MultiDexApplication() {

    private val appModule: AppModule by lazy {
        AppModule(this)
    }

    val mapSelectComponent: MapSelectComponent by lazy {
        DaggerMapSelectComponent.builder()
            .appModule(appModule)
            .build()
    }

    val taskManagerPostComponent: TaskManagerPostComponent by lazy {
        DaggerTaskManagerPostComponent.builder()
            .appModule(appModule)
            .build()
    }

    val taskManagerComponent: TaskManagerComponent by lazy {
        DaggerTaskManagerComponent.builder()
            .appModule(appModule)
            .build()
    }

    val taskWorkerReportPostComponent: TaskWorkerReportPostComponent by lazy {
        DaggerTaskWorkerReportPostComponent.builder()
                .appModule(appModule)
                .build()
    }

    val loginComponent: LoginComponent by lazy {
        DaggerLoginComponent.builder()
                .appModule(appModule)
                .build()
    }

    val accountWorkerComponent: AccountWorkerComponent by lazy {
        DaggerAccountWorkerComponent.builder()
                .appModule(appModule)
                .build()
    }

    val taskWorkerComponent: TaskWorkerComponent by lazy {
        DaggerTaskWorkerComponent.builder()
            .appModule(appModule)
            .build()
    }

    companion object {
        fun log(vararg values: Any?){
            Log.d("halo", values.joinToString(separator = " "))
        }
    }

}