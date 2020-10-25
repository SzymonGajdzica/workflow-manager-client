package pl.polsl.workflow.manager.client.model.remote.mapper

import pl.polsl.workflow.manager.client.model.data.Task
import pl.polsl.workflow.manager.client.model.data.TaskManagerReport
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReport
import pl.polsl.workflow.manager.client.model.data.TaskWorkerReportPost
import pl.polsl.workflow.manager.client.model.remote.data.*
import java.time.Instant

fun TaskApiModel.map(tasks: Map<Long, TaskApiModel>?, localizations: Map<Long, LocalizationApiModel>): Task {
    return Task(
        id = id,
        assignDate = assignDate,
        description = description,
        autoAssign = autoAssign,
        createDate = createDate,
        deadline = deadline,
        estimatedExecutionTime = Instant.ofEpochMilli(estimatedExecutionTimeInMillis),
        localization = localizations.getValue(localizationId).map(),
        name = name,
        sharedTaskId = sharedTaskId,
        startDate = startDate,
        taskManagerReport = taskManagerReportModel?.map(tasks, localizations),
        taskWorkerReportApi = taskWorkerReportApiModel?.map(),
        workerId = workerId,
        groupId = groupId,
    )
}

fun TaskWorkerReportApiModel.map(): TaskWorkerReport {
    return TaskWorkerReport(
        id = id,
        description = description,
        date = date,
        success = success
    )
}

fun TaskManagerReportApiModel.map(tasks: Map<Long, TaskApiModel>?, localizations: Map<Long, LocalizationApiModel>): TaskManagerReport {
    return TaskManagerReport(
        id = id,
        date = date,
        description = description,
        fixTask = fixTaskId?.let { tasks?.get(it) }?.map(tasks, localizations)
    )
}

fun TaskWorkerReportPost.map(): TaskWorkerReportApiModelPost {
    return TaskWorkerReportApiModelPost(
        description = description,
        success = success,
        taskId = task.id
    )
}