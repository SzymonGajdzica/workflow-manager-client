package pl.polsl.workflow.manager.client.ui.worker.task.report.post

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.model.RepositoryResult
import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReportPost
import pl.polsl.workflow.manager.client.model.data.checkDistance
import pl.polsl.workflow.manager.client.model.repository.TaskRepository
import pl.polsl.workflow.manager.client.util.extension.get
import pl.polsl.workflow.manager.client.util.extension.hasLocationPermission
import pl.polsl.workflow.manager.client.util.location.LocationReader
import pl.polsl.workflow.manager.client.util.validator.InputValidator
import javax.inject.Inject

class TaskWorkerReportPostViewModelImpl @Inject constructor(
        private val app: Application,
        private val taskRepository: TaskRepository,
        private val locationReader: LocationReader,
        private val inputValidator: InputValidator
): TaskWorkerReportPostViewModel(app) {

    override val task: MutableLiveData<Task> = MutableLiveData()
    override val descriptionInputError: MutableLiveData<String> = MutableLiveData(null)

    @SuppressLint("MissingPermission")
    override fun sendReport(taskWorkerReportPost: TaskWorkerReportPost) = launchWithLoader {
        descriptionInputError.value = inputValidator.validateBlankText(taskWorkerReportPost.description)
        if(descriptionInputError.value == null) {
            descriptionInputError.value = null
            val currentLatLng = if (app.hasLocationPermission())
                locationReader.getLastLatLng()
            else
                null
            if (currentLatLng == null)
                showErrorMessage(getString(R.string.couldNotLoadCurrentLocation))
            else if (!taskWorkerReportPost.task.localization.checkDistance(currentLatLng)) {
                showErrorMessage(getString(R.string.tooFarFromTaskDestination))
            } else when (val result = taskRepository.sendTaskReport(taskWorkerReportPost)) {
                is RepositoryResult.Success -> {
                    showSuccessMessage(getString(R.string.taskFinished))
                    finishFragment()
                }
                is RepositoryResult.Error -> showErrorMessage(result.error)
            }
        }
    }

    override fun updateArguments(bundle: Bundle) {
        super.updateArguments(bundle)
        task.value = bundle.get()
    }

}
