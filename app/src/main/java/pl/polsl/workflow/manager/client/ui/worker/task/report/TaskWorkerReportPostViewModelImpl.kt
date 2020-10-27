package pl.polsl.workflow.manager.client.ui.worker.task.report

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.hasLocationPermission
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReportPost
import pl.polsl.workflow.manager.client.model.data.checkDistance
import pl.polsl.workflow.manager.client.model.remote.RepositoryResult
import pl.polsl.workflow.manager.client.model.remote.repository.TaskRepository
import pl.polsl.workflow.manager.client.utils.LocationReader
import javax.inject.Inject

class TaskWorkerReportPostViewModelImpl @Inject constructor(
        private val app: Application,
        private val taskRepository: TaskRepository,
        private val locationReader: LocationReader
): TaskWorkerReportPostViewModel(app) {

    override val descriptionInputError: MutableLiveData<String> = MutableLiveData(null)

    @SuppressLint("MissingPermission")
    override fun sendReport(taskWorkerReportPost: TaskWorkerReportPost) {
        if(taskWorkerReportPost.description.isBlank())
            descriptionInputError.value = getString(R.string.cannotBeBlank)
        else {
            launchWithLoader {
                val currentLatLng = if(app.hasLocationPermission())
                    locationReader.getLastLatLng()
                else
                    null
                if(currentLatLng == null)
                    showToast(getString(R.string.couldNotLoadCurrentLocation))
                else if(!taskWorkerReportPost.task.localization.checkDistance(currentLatLng)) {
                    showToast(getString(R.string.tooFarFromTaskDestination))
                } else when(val result = taskRepository.sendTaskReport(taskWorkerReportPost)) {
                    is RepositoryResult.Success -> {
                        showToast(getString(R.string.taskFinished))
                        finishFragment()
                    }
                    is RepositoryResult.Error -> showToast(result.error)
                }
            }
        }
    }

    override fun clearErrorMessages() {
        super.clearErrorMessages()
        descriptionInputError.value = null
    }

}